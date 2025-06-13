package com.campusconnect.campusconnect_auth.controller;

import com.campusconnect.campusconnect_auth.dto.ApplyAdmissionRequest;
import com.campusconnect.campusconnect_auth.service.AdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admissions")
@RequiredArgsConstructor
public class AdmissionController {

    private final AdmissionService admissionService;

    /**
     * STUDENT applies for a program
     */
    @PostMapping("/apply")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> applyForProgram(@RequestBody ApplyAdmissionRequest request,
                                                  Authentication authentication) {
        String email = authentication.getName(); // from JWT
        admissionService.applyForAdmission(request, email);
        return ResponseEntity.ok("Application submitted successfully");
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getMyApplications(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(admissionService.getApplicationsForStudent(email));
    }

}
