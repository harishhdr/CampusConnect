package com.campusconnect.campusconnect_auth.controller.student;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/student/files")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentFileUploadController {

    @Value("${upload.directory}")
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<List<String>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("applicationId") Long applicationId) throws IOException {

        List<String> urls = new ArrayList<>();
        Path folderPath = Paths.get(uploadDir, "admissions", "application_" + applicationId);
        Files.createDirectories(folderPath);

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String originalName = file.getOriginalFilename();
            String uniqueName = UUID.randomUUID() + "_" + originalName;

            Path fullPath = folderPath.resolve(uniqueName);
            Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);

            urls.add("/files/view/admissions/application_" + applicationId + "/" + uniqueName);
        }

        return ResponseEntity.ok(urls);
    }
}
