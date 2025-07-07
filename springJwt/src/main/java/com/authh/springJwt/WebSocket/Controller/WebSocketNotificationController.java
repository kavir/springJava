package com.authh.springJwt.WebSocket.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.authh.springJwt.WebSocket.Model.NotificationMessage;

@Controller
public class WebSocketNotificationController {

    @MessageMapping("/bill-event") // Client sends to /app/bill-event
    @SendTo("/topic/group-updates") // Broadcast to all clients subscribed
    public NotificationMessage notifyGroup(NotificationMessage message) {
        // You can log or save the notification here
        return message; // Automatically sent to /topic/group-updates
    }
}
