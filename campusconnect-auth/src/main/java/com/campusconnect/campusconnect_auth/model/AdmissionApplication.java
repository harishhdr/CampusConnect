package com.campusconnect.campusconnect_auth.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "admission_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdmissionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 📄 Personal Info
    private String name;
    private LocalDate dob;
    private String gender;
    private String phoneNumber;
    private String address;
    private String nationality;
    private String category;
    private String aadhaarNumber;

    // 📚 Academic Info
    private String highSchoolName;
    private Float highSchoolScore;
    private String intermediateName;
    private Float intermediateScore;
    private String entranceExamName;
    private Float entranceExamScore;
    private Integer yearOfPassing;

    // 🎓 Program Info
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program programApplied;

    @ElementCollection
    private List<String> documentUrls;

    // 🔐 System Controlled
    @Enumerated(EnumType.STRING)
    private Status status;

    private String studentEmail;
    private LocalDateTime createdAt;

    public enum Status {
        PENDING, APPROVED, REJECTED
    }
}
