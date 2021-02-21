package turku.forge.babyproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

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
