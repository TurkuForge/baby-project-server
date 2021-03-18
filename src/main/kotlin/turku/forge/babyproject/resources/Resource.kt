package turku.forge.babyproject.resources

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.LinkRelation
import org.springframework.hateoas.RepresentationModel

/**
 * This is a `RepresentationModel` abstraction.
 *
 * The point of these are to be JSON representations of the values
 * we send to the api consumer. Any class that extends this will automatically
 * be serialized to a `hal+json` media container.
 *
 * since we don't yet know what media container we will use
 * this class serves as a abstraction for the media container we are currently using.
 *
 * Its likely that this will be removed once we have chosen a media container
 * but we may keep it who knows
 */
abstract class Resource<T : Resource<T>> : RepresentationModel<T>() {
    @JsonProperty("_embedded")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val embedded: MutableMap<LinkRelation, Collection<Resource<*>>> = mutableMapOf()

    fun add(rel: LinkRelation, resource: Resource<*>) {
        add(rel, mutableListOf(resource))
    }

    fun add(rel: LinkRelation, resource: Collection<Resource<*>>) {
        embedded[rel] = resource
    }
}
