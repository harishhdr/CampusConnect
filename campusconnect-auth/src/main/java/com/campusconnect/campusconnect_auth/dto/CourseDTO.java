package com.campusconnect.campusconnect_auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String code;
    private String title;
    private int credits;
    private String type;
    private String departmentName;
}
