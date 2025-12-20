package com.authh.springjwt.controller;


import com.authh.springjwt.service.NotificationService;
import com.authh.springjwt.dto.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationApiController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification,
                                                   @RequestParam String userId) {
        notificationService.sendNotificationToUser(userId,notification);
        return ResponseEntity.ok("Notification sent!");
    }
}
