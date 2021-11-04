package turku.forge.babyproject.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.LinkRelation
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.mediatype.hal.HalModelBuilder
import org.springframework.hateoas.server.core.Relation
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import turku.forge.babyproject.config.STOMP_ENDPOINT

// The request path for the root of the API
const val API_PATH = ""
const val channelRel = "channel"
val SOCK_JS_ENDPOINT = LinkRelation.of("sockjs-endpoint")

/**
 * This is the root reset controller
 * any request coming in to the root will be
 * handled by this controller
 */
@RestController
@RequestMapping(value = [API_PATH])
class RootApiController {
    /**
     * Mapping for the root of the API
     * This is the method that is run when a request is fired at the root
     */
    @GetMapping()
    fun index(): HttpEntity<RepresentationModel<IndexResource>> {
        val link = linkTo<ChannelController> { message(null, null) }

        val model = HalModelBuilder.halModel()
            .link(
                WebMvcLinkBuilder.linkTo(RootApiController::class.java)
                    .slash(STOMP_ENDPOINT)
                    .withRel(SOCK_JS_ENDPOINT)
            )
            .link(WebMvcLinkBuilder.linkTo(RootApiController::class.java).withSelfRel())
            .link(link.withRel(channelRel))

        getChannels().forEach {
            val channelLink = linkTo<ChannelController> { message(it, null) };
            val channel = Channel(it, channelLink.toUriComponentsBuilder().build().path)
            channel.add(channelLink.withSelfRel())
            model.embed(channel)
        }

        return ResponseEntity(model.build(), HttpStatus.OK);
    }

    /**
     * Currently we don't have a database so we have created a temp method that returns
     * a hardcoded list of channels, In the future this method will get channels from a database or cache
     */
    private fun getChannels(): List<String> {
        return listOf("general", "random")
    }
}

@Relation(IanaLinkRelations.INDEX_VALUE)
@JsonInclude(Include.NON_NULL)
class IndexResource : RepresentationModel<IndexResource>()

@Relation(collectionRelation = channelRel)
@JsonInclude(Include.NON_NULL)
data class Channel(
    val name: String,
    val subscription: String? = null
) : RepresentationModel<Channel>()
