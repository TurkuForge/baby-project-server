package turku.forge.babyproject.api

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import turku.forge.babyproject.resources.Resource
import java.util.*

const val CHANNEL_PATH = "$API_PATH/channel"


/**
 * This is a rest controller for the channels
 * channels anything that follows
 * @see CHANNEL_PATH
 * will be routed to this controller
 */
@RestController
@RequestMapping( value = [CHANNEL_PATH] )
class ChannelController {
    @Autowired
    private lateinit var template: SimpMessagingTemplate

    @PostMapping("/{channelName}", consumes = ["application/json"])
    fun message(@PathVariable(value = "channelName") channelName: String?, @RequestBody message: Message?): ResponseEntity<*> {
        message?.let {
            val destination = "$CHANNEL_PATH/$channelName"
            template.convertAndSend(destination, OutputMessage(it))
            return ResponseEntity(null, HttpStatus.OK)
        }

        return ResponseEntity(null, HttpStatus.BAD_REQUEST)
    }
}

/** @see Resource */
@JsonInclude(JsonInclude.Include.NON_NULL)
class Message(val content: String, val sender: String)

/** @see Resource */
class OutputMessage(inComingMessage: Message) {
    val message: String = inComingMessage.content
    val from: String = inComingMessage.sender
    val date = Date().time
}
