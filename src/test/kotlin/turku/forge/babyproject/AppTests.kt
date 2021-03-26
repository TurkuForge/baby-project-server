package turku.forge.babyproject

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import turku.forge.babyproject.api.ChannelController
import turku.forge.babyproject.api.RootApiController

@SpringBootTest
class AppTests {

    @Autowired
    lateinit var rootController: RootApiController

    @Autowired
    lateinit var channelController: ChannelController

    @Test
    fun `Validates context loads`() {
        Assertions.assertThat(rootController).isNotNull
        Assertions.assertThat(channelController).isNotNull
    }
}
