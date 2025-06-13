package com.campusconnect.campusconnect_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseRegistrationResponse {
    private String courseCode;
    private String title;
    private int credits;
    private String department;
    private int semester;
    private String status;
}
