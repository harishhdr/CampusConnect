package com.campusconnect.campusconnect_auth.controller.student;

import com.campusconnect.campusconnect_auth.dto.ApplyAdmissionRequest;
import com.campusconnect.campusconnect_auth.service.AdmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/student/admissions")
@RequiredArgsConstructor
public class StudentAdmissionController {

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

    @PatchMapping("/{id}/documents")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> updateDocuments(
            @PathVariable Long id,
            @RequestBody List<String> documentUrls,
            Authentication authentication) {

        String email = authentication.getName();
        admissionService.updateApplicationDocuments(id, email, documentUrls);
        return ResponseEntity.ok("📎 Document URLs updated successfully.");
    }

    @PatchMapping("/{id}/documents/upload")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> uploadAndSaveDocuments(
            @PathVariable Long id,
            @RequestParam("files") MultipartFile[] files,
            Authentication authentication) throws IOException {

        String studentEmail = authentication.getName();
        admissionService.uploadDocumentsAndSaveUrls(id, studentEmail, files);

        return ResponseEntity.ok("📎 Files uploaded and document URLs saved.");
    }



    @GetMapping("/my")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getMyApplications(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(admissionService.getApplicationsForStudent(email));
    }

}
