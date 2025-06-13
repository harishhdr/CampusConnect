package com.campusconnect.campusconnect_auth.service;

import com.campusconnect.campusconnect_auth.dto.AdmissionResponseDTO;
import com.campusconnect.campusconnect_auth.model.AdmissionApplication;
import com.campusconnect.campusconnect_auth.repository.AdmissionApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficerAdmissionService {

    private final AdmissionApplicationRepository applicationRepository;

    // Get all applications
    public List<AdmissionResponseDTO> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(AdmissionResponseDTO::fromEntity)
                .toList();
    }

    // Approve or reject application
    public void updateApplicationStatus(Long id, String statusStr) {
        AdmissionApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        AdmissionApplication.Status status = AdmissionApplication.Status.valueOf(statusStr.toUpperCase());
        app.setStatus(status);
        applicationRepository.save(app);
    }
}
