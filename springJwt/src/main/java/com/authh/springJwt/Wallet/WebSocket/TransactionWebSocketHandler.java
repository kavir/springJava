package com.authh.springJwt.Wallet.WebSocket;

// TransactionWebSocketHandler.java
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import io.micrometer.common.lang.NonNull;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransactionWebSocketHandler extends TextWebSocketHandler {
    
    private static List<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("WebSocket session established: " + session.getId());
    }

    @Override
    public void handleTextMessage(@NonNull WebSocketSession session,@NonNull TextMessage message) throws Exception {
        // Handle incoming messages if needed
        System.out.println("Received message: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("WebSocket session closed: " + session.getId());
    }
//aahaha
    // Static method to send messages to all connected clients
    public static void sendTransactionUpdate(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                System.err.println("Error sending WebSocket message: " + e.getMessage());
            }
        }
    }
}

