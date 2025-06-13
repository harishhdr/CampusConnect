package com.campusconnect.campusconnect_auth.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.PathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/files")
public class FileViewController {

    @Value("${upload.directory}")
    private String uploadDir;

    @GetMapping("/view/admissions/application_{appId}/{filename:.+}")
    public void viewFile(
            @PathVariable String appId,
            @PathVariable String filename,
            HttpServletResponse response) throws IOException {

        Path filePath = Path.of(uploadDir, "admissions", "application_" + appId, filename);
        PathResource resource = new PathResource(filePath);

        if (resource.exists()) {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            resource.getInputStream().transferTo(response.getOutputStream());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
