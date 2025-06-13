package com.campusconnect.campusconnect_auth.dto;

import lombok.Data;



@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;
    // Example: ["STUDENT", "HOSTEL_MANAGER"]
}
