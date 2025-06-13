package com.campusconnect.campusconnect_auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_registrations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Course course;

    private int semester;
    private String status; // REGISTERED / DROPPED / COMPLETED
}
