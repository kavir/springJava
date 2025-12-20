package com.authh.springjwt.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class BillResponse {
    private Long billId;
    private String title;
    private Double totalAmount;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<ParticipantStatus> participants;
}

