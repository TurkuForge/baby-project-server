package turku.forge.babyproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


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
