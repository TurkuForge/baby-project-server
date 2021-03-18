package turku.forge.babyproject.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import turku.forge.babyproject.ConfigProperties

/**
 * This class enables security rules on the separate routes.
 * currently a bit bare bones but this is where the route security should be handled
 */
@Configuration
@EnableWebMvc
class Security(
        @Autowired
        private val config: ConfigProperties
) : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        val corsRegistry = registry.addMapping("/**")

        config.cors.forEach {
            corsRegistry.allowedOriginPatterns(it)
        }
    }
}
