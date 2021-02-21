package turku.forge.babyproject.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

const val API_PATH = "/"

/**
 * Rest control for the root of the api.
 * if you serve this api under `api.example.com` all the request going to `/`
 * will be routed to this controller.
 */
@RestController
class RootApiController {
    @GetMapping(API_PATH)
    fun index(): String {
        return "Hello World"
    }
}
