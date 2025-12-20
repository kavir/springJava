package com.authh.springjwt.dto;

import com.authh.springjwt.Authentication.model.Role;
import lombok.*;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"sender", "receiver"})
public class UserAdminDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String number;
    private String profilePicture;
    private Double rewardPoints;
    private Role role;
}
