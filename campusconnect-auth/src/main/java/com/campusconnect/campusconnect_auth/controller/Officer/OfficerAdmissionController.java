package com.campusconnect.campusconnect_auth.controller.Officer;


import com.campusconnect.campusconnect_auth.dto.AdmissionResponseDTO;
import com.campusconnect.campusconnect_auth.service.OfficerAdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/officer/admissions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMISSION_OFFICER')")
public class OfficerAdmissionController {

    private final OfficerAdmissionService officerAdmissionService;

    @GetMapping("/all")
    public ResponseEntity<List<AdmissionResponseDTO>> getAllApplications() {
        return ResponseEntity.ok(officerAdmissionService.getAllApplications());
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveApplication(@PathVariable Long id) {
        officerAdmissionService.updateApplicationStatus(id, "APPROVED");
        return ResponseEntity.ok("✅ Application approved");
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<String> rejectApplication(@PathVariable Long id) {
        officerAdmissionService.updateApplicationStatus(id, "REJECTED");
        return ResponseEntity.ok("❌ Application rejected");
    }

}
