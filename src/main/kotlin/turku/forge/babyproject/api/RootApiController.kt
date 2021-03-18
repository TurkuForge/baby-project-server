package turku.forge.babyproject.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import org.springframework.hateoas.LinkRelation
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import turku.forge.babyproject.config.STOMP_ENDPOINT
import turku.forge.babyproject.resources.Resource

// The request path for the root of the API
const val API_PATH = ""

// Relation constants
const val WS_RELATION = "ws:connect"
const val CHANNEL_RELATION = "channel"
const val INDEX_RELATION = "index"

/**
 * Rest control for the root of the api.
 * if you serve this api under `api.example.com` all the request going to `/`
 * will be routed to this controller.
 */
@RestController
@RequestMapping(value = [API_PATH])
class RootApiController {
    /**
     * Mapping for the root of the API
     * This is the method that is ran when a request is fired at the root
     */
    @GetMapping()
    fun index(): ResponseEntity<IndexResource> {
        val resource = IndexResource()
        resource.add(WebMvcLinkBuilder.linkTo(RootApiController::class.java).withSelfRel())
        resource.add(
            WebMvcLinkBuilder.linkTo(RootApiController::class.java)
                .slash(STOMP_ENDPOINT)
                .withRel(WS_RELATION)
        )

        val channelRelation = LinkRelation.of(CHANNEL_RELATION)
        val channelCollection: Collection<ChannelResource> = getChannels().map {
            val link = linkTo<ChannelController> { message(it, null) }
            val channelResource = ChannelResource(it, link.toUriComponentsBuilder().build().path)
            channelResource.add(link.withSelfRel())
        }
        resource.add(channelRelation, channelCollection)

        return ResponseEntity(resource, HttpStatus.OK);
    }

    /**
     * Currently we don't have a database so we have created a temp method that returns
     * a hardcoded list of channels, In the future this method will get channels from a database or cache
     */
    private fun getChannels(): List<String> {
        return listOf("general", "random")
    }
}

/**
 * This is the index resource for this file
 * since we don't have any default values we want to pass from the root
 * as of yet we have no need to move this to its own file.
 */
@Relation(INDEX_RELATION)
@JsonInclude(Include.NON_NULL)
class IndexResource : Resource<IndexResource>()

@Relation(CHANNEL_RELATION)
@JsonInclude(Include.NON_NULL)
class ChannelResource(val name: String, val subscription: String? = null) : Resource<ChannelResource>()
