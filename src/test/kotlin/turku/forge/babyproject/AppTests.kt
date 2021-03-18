package turku.forge.babyproject

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import turku.forge.babyproject.api.RootApiController

@SpringBootTest
class AppTests {

	@Autowired
	lateinit var controller: RootApiController

	@Test
	fun `context loads`() {
		Assertions.assertThat(controller).isNotNull
	}
}
