package turku.forge.babyproject

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BabyProjectApplicationTests {

	@Test
	fun contextLoads() {
		assertThat( true ).isTrue
	}

}
