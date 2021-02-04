package turku.forge.babyproject.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

const val API_PATH = "/"

@RestController
class RootApiController {
    @GetMapping( API_PATH )
    fun index(): String {

        return "Hello World"
    }
}
