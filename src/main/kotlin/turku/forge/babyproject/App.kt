package turku.forge.babyproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.UriTemplate
import org.springframework.hateoas.mediatype.hal.CurieProvider
import org.springframework.hateoas.mediatype.hal.DefaultCurieProvider

const val CURIE_NAMESPACE = "bp"

@SpringBootApplication
class App {
    @Bean
    fun curieProvider(): CurieProvider {
        return DefaultCurieProvider(
            CURIE_NAMESPACE,
            // Location of CURIE docs
            UriTemplate.of("https://docs.turkuforge.fi/{#rel}")
        )
    }
}
/**
 * Initialization class
 * This class gets ran when the servlet container starts.
 * in our case its when Tomcat starts, or `./gradlew bootRun` runs
 */
fun main(args: Array<String>) {
    runApplication<App>(*args)
}

/**
 * This class pulls in config properties from
 * `application.yml` and serialises them so we can use them
 * throughout the application
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "babyproject.config")
class ConfigProperties(val cors: List<String> = arrayListOf())
