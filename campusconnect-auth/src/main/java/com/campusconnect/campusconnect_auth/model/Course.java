package com.campusconnect.campusconnect_auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;       // e.g., CSE101
    private String title;      // e.g., Data Structures
    private int credits;       // e.g., 4

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private Type type;         // CORE / ELECTIVE

    public enum Type {
        CORE,
        ELECTIVE
    }
}
