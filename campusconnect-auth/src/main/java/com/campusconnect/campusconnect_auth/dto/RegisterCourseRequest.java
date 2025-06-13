package com.campusconnect.campusconnect_auth.dto;

import lombok.Data;

@Data
public class RegisterCourseRequest {
    private Long courseId;
    private int semester;
}
