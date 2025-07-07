package com.authh.springJwt.WebSocket.Model;

import lombok.Data;

@Data
public class NotificationMessage {
    private String groupId;
    private String content;
    private String type; // e.g., "BILL_ADDED", "BILL_PAID", etc.
    private String sender;

    // Constructors, Getters, Setters
}
