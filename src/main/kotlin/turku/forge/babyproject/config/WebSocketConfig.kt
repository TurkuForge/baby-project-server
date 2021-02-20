package turku.forge.babyproject.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

const val STOMP_ENDPOINT = "/connect"
const val APPLICATION_DESTINATIONS = "/message"

/**
 * This is a configuration where we extend and override the base `WebSocketMessageBrokerConfigurer`
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
       registry.addEndpoint( STOMP_ENDPOINT )
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes( APPLICATION_DESTINATIONS )
    }
}
