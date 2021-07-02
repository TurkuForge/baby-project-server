package turku.forge.babyproject.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import turku.forge.babyproject.ConfigProperties

@EnableConfigurationProperties(value = [ConfigProperties::class])
@TestPropertySource("classpath:application.yml")
@WebMvcTest(RootApiController::class)
class RootApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Validates the http status code and json body`() {
        val expectedResult =
            """{"_embedded":{"bp:channel":[{"name":"general","subscription":"/channel/general","_links":{"self":{"href":"http://localhost/channel/general"}}},{"name":"random","subscription":"/channel/random","_links":{"self":{"href":"http://localhost/channel/random"}}}]},"_links":{"bp:sockjs-endpoint":{"href":"http://localhost/connect"},"self":{"href":"http://localhost"},"bp:channel":{"href":"http://localhost/channel/{channelName}","templated":true},"curies":[{"href":"https://docs.turkuforge.fi/{#rel}","name":"bp","templated":true}]}}""".trimIndent()
        mockMvc.perform(get("$API_PATH/"))
            .andExpect(status().isOk)
            .andExpect(content().json(expectedResult, true))
    }
}
