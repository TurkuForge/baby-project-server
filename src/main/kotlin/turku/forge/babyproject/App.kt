package turku.forge.babyproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration


@SpringBootApplication
class App

/**
 * Initialization class
 * This class gets ran when the servlet container starts.
 * in our case its when Tomcat starts, or `./gradlew bootRun` runs
 */
fun main(args: Array<String>) {
    runApplication<App>(*args)
}


@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "spring.config")
class ConfigProperties(val cors: List<String> = arrayListOf())
