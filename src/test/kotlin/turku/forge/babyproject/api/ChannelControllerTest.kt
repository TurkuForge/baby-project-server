package turku.forge.babyproject.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import org.springframework.web.socket.sockjs.client.SockJsClient
import org.springframework.web.socket.sockjs.client.WebSocketTransport
import turku.forge.babyproject.config.STOMP_ENDPOINT
import java.lang.reflect.Type
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChannelControllerTest {

    @LocalServerPort
    private val port: Int? = null

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    lateinit var webSocketStompClient: WebSocketStompClient
    lateinit var objectMapper: ObjectMapper
    lateinit var localhost: String

    @BeforeEach
    fun setup() {
        localhost = "http://localhost:$port"
        objectMapper = ObjectMapper()
        webSocketStompClient = WebSocketStompClient(
            SockJsClient(
                listOf(WebSocketTransport(StandardWebSocketClient()))
            )
        )
    }

    @Test
    fun `Validates the http and websocket communication mechanism`() {
        val blockingQueue = ArrayBlockingQueue<JsonNode>(1)
        val session = webSocketStompClient.connect(
            "$localhost$STOMP_ENDPOINT",
            object : StompSessionHandlerAdapter() {})[1, TimeUnit.SECONDS]

        session.subscribe("$CHANNEL_PATH/general", object : StompFrameHandler {
            override fun getPayloadType(headers: StompHeaders): Type {
                return ByteArray::class.java
            }

            override fun handleFrame(headers: StompHeaders, payload: Any?) {
                blockingQueue.add(objectMapper.readTree(payload as ByteArray))
            }
        })

        restTemplate.postForEntity(
            "$localhost$CHANNEL_PATH/general",
            HttpEntity<Message>(Message("hello world!", "me")),
            ResponseEntity::class.java
        )

        val outputMessage = blockingQueue.poll(1, TimeUnit.SECONDS)

        Assertions.assertEquals("hello world!", outputMessage?.path("message")?.textValue())
        Assertions.assertEquals("me", outputMessage?.path("from")?.textValue())
    }
}
