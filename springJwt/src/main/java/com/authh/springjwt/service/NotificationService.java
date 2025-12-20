package com.authh.springjwt.service;

import com.authh.springjwt.dto.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Send notification to a specific user
     *
     * @param usernameOrUserId Unique user identifier (can be email, username, or userId)
     * @param notification     Notification DTO
     */
    public void sendNotificationToUser(String usernameOrUserId, Notification notification) {
        messagingTemplate.convertAndSendToUser(
                usernameOrUserId,
                "/queue/notifications",
                notification
        );
    }
}
