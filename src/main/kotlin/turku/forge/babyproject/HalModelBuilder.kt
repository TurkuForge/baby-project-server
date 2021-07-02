/**
 * Temporarily Copied the next version of the HalModelBuilder in order to
 * fix bug https://github.com/TurkuForge/baby-project-server/issues/14 this will be removed
 * as soon as the next version of the library has been released.
 */
package turku.forge.babyproject

import com.fasterxml.jackson.annotation.JsonUnwrapped
import org.springframework.hateoas.*
import org.springframework.hateoas.server.core.EmbeddedWrappers
import org.springframework.lang.Nullable
import org.springframework.util.Assert
import java.util.stream.Collectors
import java.util.stream.Stream

class HalModelBuilder private constructor(private val wrappers: EmbeddedWrappers) {
    private var model: Any? = null
    private var links = Links.NONE
    private val embeddeds: MutableList<Any> = ArrayList()

    fun entity(entity: Any): HalModelBuilder {
        Assert.notNull(entity, "Entity must not be null!")
        check(model == null) { "Model object already set!" }
        model = entity
        return this
    }

    fun embed(entity: Any, linkRelation: LinkRelation): HalModelBuilder {
        Assert.notNull(entity, "Entity must not be null!")
        Assert.notNull(linkRelation, "Link relation must not be null!")
        embeddeds.add(wrappers.wrap(entity, linkRelation))
        return this
    }

    fun embed(entity: Any): HalModelBuilder {
        Assert.notNull(entity, "Entity must not be null!")
        embeddeds.add(wrappers.wrap(entity))
        return this
    }

    @JvmOverloads
    fun embed(collection: Collection<Any>, type: Class<*> = Void::class.java): HalModelBuilder {
        Assert.notNull(collection, "Collection must not be null!")
        Assert.notNull(type, "Type must not be null!")
        if (!collection.isEmpty()) {
            return embed(wrappers.wrap(collection))
        }
        return if (Void::class.java == type) this else embed(wrappers.emptyCollectionOf(type))
    }

    fun embed(collection: Collection<*>, relation: LinkRelation): HalModelBuilder {
        Assert.notNull(collection, "Collection must not be null!")
        Assert.notNull(relation, "Link relation must not be null!")
        return embed(wrappers.wrap(collection, relation))
    }

    @JvmOverloads
    fun embed(stream: Stream<*>, type: Class<*> = Void::class.java): HalModelBuilder {
        Assert.notNull(stream, "Stream must not be null!")
        Assert.notNull(type, "Type must not be null!")
        return embed(stream.collect(Collectors.toList()), type)
    }

    fun embed(stream: Stream<*>, relation: LinkRelation): HalModelBuilder? {
        Assert.notNull(stream, "Stream must not be null!")
        Assert.notNull(relation, "Link relation must not be null!")
        return relation?.let { embed(stream.collect(Collectors.toList()), it) }
    }

    fun preview(entity: Any): PreviewBuilder {
        Assert.notNull(entity, "Preview entity must not be null!")
        return object : PreviewBuilder {
            override fun forLink(link: Link?): HalModelBuilder? {
                return link?.let {
                    previewFor(
                        entity,
                        it
                    )
                }
            }
        }
    }

    fun preview(collection: Collection<*>): PreviewBuilder {
        Assert.notNull(collection, "Preview collection must not be null!")
        return object : PreviewBuilder {
            override fun forLink(link: Link?): HalModelBuilder? {
                return link?.let {
                    previewFor(
                        collection,
                        it
                    )
                }
            }
        }
    }

    fun preview(collection: Collection<*>, type: Class<*>): PreviewBuilder {
        Assert.notNull(collection, "Preview collection must not be null!")
        Assert.notNull(type, "Type must not be null!")
        return object : PreviewBuilder {
            override fun forLink(link: Link?): HalModelBuilder? {
                return link?.let {
                    previewFor(
                        type,
                        it
                    )
                }
            }
        }
    }

    fun link(link: Link?): HalModelBuilder {
        links = links.and(link)
        return this
    }

    fun link(href: String, relation: LinkRelation): HalModelBuilder {
        return link(Link.of(href, relation))
    }

    fun links(links: Iterable<Link>): HalModelBuilder {
        this.links = this.links.and(links)
        return this
    }

    fun <T : RepresentationModel<T>?> build(): RepresentationModel<T> {
        return HalRepresentationModel(model, embeddeds, links) as RepresentationModel<T>
    }

    private fun previewFor(entity: Any, link: Link): HalModelBuilder {
        link(link)
        embed(entity, link.rel)
        return this
    }

    private class HalRepresentationModel<T> private constructor(@Nullable entity: T, embeddeds: List<Any>) :
        EntityModel<T>() {
        @Nullable
        private val entity: T
        private val embeddeds: List<Any>

        constructor(@Nullable entity: T, embeddeds: List<Any>, links: Links) : this(entity, embeddeds) {
            add(links)
        }

        @Nullable
        override fun getContent(): T {
            return entity
        }

        @JsonUnwrapped
        @SuppressWarnings("deprecation")
        fun getEmbeddeds(): CollectionModel<Any> {
            return object : CollectionModel<Any>(embeddeds) {
                override fun add(link: Link): CollectionModel<Any> {
                    this@HalRepresentationModel.add(link)
                    return this
                }
            }
        }

        init {
            Assert.notNull(embeddeds, "Embedds must not be null!")
            this.entity = entity
            this.embeddeds = embeddeds
        }
    }

    interface PreviewBuilder {
        fun forLink(link: Link?): HalModelBuilder?

        fun forLink(href: String, relation: LinkRelation): HalModelBuilder? {
            return forLink(Link.of(href, relation))
        }
    }

    companion object {
        fun halModel(): HalModelBuilder {
            return HalModelBuilder(EmbeddedWrappers(false))
        }

        fun halModel(wrappers: EmbeddedWrappers): HalModelBuilder {
            Assert.notNull(wrappers, "EmbeddedWrappers must not be null!")
            return HalModelBuilder(wrappers)
        }

        fun halModelOf(entity: Any): HalModelBuilder {
            return halModel().entity(entity)
        }

        fun emptyHalModel(): HalModelBuilder {
            return halModel()
        }
    }
}
