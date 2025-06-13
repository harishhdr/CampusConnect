package com.campusconnect.campusconnect_auth.repository;

import com.campusconnect.campusconnect_auth.model.AdmissionApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdmissionApplicationRepository extends JpaRepository<AdmissionApplication, Long> {

    List<AdmissionApplication> findByStudentEmail(String email);

    // ✅ NEW: Check if a student has already applied to a specific program
    boolean existsByStudentEmailAndProgramApplied_Id(String studentEmail, Long programId);
}
