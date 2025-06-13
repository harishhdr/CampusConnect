package com.campusconnect.campusconnect_auth.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ApplyAdmissionRequest {
    private String name;
    private LocalDate dob;
    private String gender;
    private String phoneNumber;
    private String address;
    private String nationality;
    private String category;
    private String aadhaarNumber;

    private String highSchoolName;
    private Float highSchoolScore;
    private String intermediateName;
    private Float intermediateScore;
    private String entranceExamName;
    private Float entranceExamScore;
    private Integer yearOfPassing;

    private Long programId;
    private List<String> documentUrls;
}
