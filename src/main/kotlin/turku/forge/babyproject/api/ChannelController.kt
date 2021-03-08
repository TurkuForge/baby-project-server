package turku.forge.babyproject.api

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import turku.forge.babyproject.config.SIMPLE_MESSAGE_BROKER_ENDPOINT
import java.util.*

@RestController
class ChannelController {
    @Autowired
    private lateinit var template: SimpMessagingTemplate

    @PostMapping("$SIMPLE_MESSAGE_BROKER_ENDPOINT/{channelName}", consumes = ["application/json"])
    fun message(@PathVariable(value = "channelName") channelName: String?, @RequestBody message: Message?): ResponseEntity<*> {
        message?.let {
            val destination = "$SIMPLE_MESSAGE_BROKER_ENDPOINT/$channelName"
            template.convertAndSend(destination, OutputMessage(it))
            return ResponseEntity(null, HttpStatus.OK)
        }

        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }
}

/**
 * A POJO for messages
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Message(val content: String, val sender: String)

class OutputMessage(inComingMessage: Message) {
    val message: String = inComingMessage.content
    val from: String = inComingMessage.sender
    val date = Date().time
}
