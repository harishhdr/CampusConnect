package com.campusconnect.campusconnect_auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "programs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer duration; // in years
    private String eligibilityCriteria;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
