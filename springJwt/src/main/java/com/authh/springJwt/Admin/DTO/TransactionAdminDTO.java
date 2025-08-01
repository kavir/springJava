package com.authh.springJwt.Admin.DTO;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"sender", "receiver"})
public class TransactionAdminDTO {

    private Long id;

    private String senderFirstname;
    private String senderLastname;
    private String senderNumber;

    private String receiverFirstname;
    private String receiverLastname;
    private String receiverNumber;

    private Double amount;

    private LocalDateTime timestamp;

    private String status;

    private String statement;
}
