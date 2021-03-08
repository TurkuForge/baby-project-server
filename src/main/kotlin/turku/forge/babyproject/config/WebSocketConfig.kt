package turku.forge.babyproject.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import turku.forge.babyproject.api.API_PATH


const val STOMP_ENDPOINT = "$API_PATH/connect"
const val SIMPLE_MESSAGE_BROKER_ENDPOINT = "$API_PATH/channel"

/**
 * This is a configuration where we extend and override the base `WebSocketMessageBrokerConfigurer`
 */
@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {
    override fun configureMessageBroker(config: MessageBrokerRegistry) {
        config.enableSimpleBroker(SIMPLE_MESSAGE_BROKER_ENDPOINT)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(STOMP_ENDPOINT)
                // @TODO: get allowed origins from config file
                .setAllowedOriginPatterns("*")
                .withSockJS()
    }
}
