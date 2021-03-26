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
            """{"_links":{"self":{"href":"http://localhost"},"bp:sockjs-endpoint":{"href":"http://localhost/connect"}},"_embedded":{"bp:channelList":[{"name":"general","subscription":"/channel/general","_links":{"self":{"href":"http://localhost/channel/general"}}},{"name":"random","subscription":"/channel/random","_links":{"self":{"href":"http://localhost/channel/random"}}}]}}"""
        mockMvc.perform(get("$API_PATH/"))
            .andExpect(status().isOk)
            .andExpect(content().json(expectedResult))
    }
}
