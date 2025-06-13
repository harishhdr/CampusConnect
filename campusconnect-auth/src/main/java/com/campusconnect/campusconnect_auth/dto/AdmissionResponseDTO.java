package com.campusconnect.campusconnect_auth.dto;

import com.campusconnect.campusconnect_auth.model.AdmissionApplication;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class AdmissionResponseDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private String gender;
    private String phoneNumber;
    private String category;
    private String status;
    private String programName;
    private String departmentName;
    private LocalDateTime createdAt;
    private List<String> documentUrls;

    // ✅ Add this method
    public static AdmissionResponseDTO fromEntity(AdmissionApplication app) {
        return new AdmissionResponseDTO(
                app.getId(),
                app.getName(),
                app.getDob(),
                app.getGender(),
                app.getPhoneNumber(),
                app.getCategory(),
                app.getStatus().name(),
                app.getProgramApplied().getName(),
                app.getProgramApplied().getDepartment().getName(),
                app.getCreatedAt(),
                app.getDocumentUrls()
        );
    }
}
