package turku.forge.babyproject.resources

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.LinkRelation
import org.springframework.hateoas.RepresentationModel

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
