package mx.utez.simapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import mx.utez.simapi.models.Message;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Message send(final Message message) throws Exception {
        return message;
    }

    @MessageMapping("/private")
    public void sendPrivate(final Message message) throws Exception {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
