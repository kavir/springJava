package com.authh.springjwt.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.authh.springjwt.model.NotificationMessage;

@Controller
public class WebSocketNotificationController {

    @MessageMapping("/bill-event")
    @SendTo("/topic/group-updates")
    public NotificationMessage notifyGroup(NotificationMessage message) {
        return message;
    }
}
