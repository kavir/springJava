package com.authh.springJwt.WebSocket.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.authh.springJwt.WebSocket.Model.NotificationMessage;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendBillUpdate(String groupId, String content, String sender, String type) {
        NotificationMessage message = new NotificationMessage();
        message.setGroupId(groupId);
        message.setContent(content);
        message.setSender(sender);
        message.setType(type);
        System.out.println("Sending WebSocket notification: " + message);
        messagingTemplate.convertAndSend("/topic/group-updates", message);
    }
}
