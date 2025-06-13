package com.campusconnect.campusconnect_auth.service;

import com.campusconnect.campusconnect_auth.dto.AdmissionResponseDTO;
import com.campusconnect.campusconnect_auth.dto.ApplyAdmissionRequest;
import com.campusconnect.campusconnect_auth.model.AdmissionApplication;
import com.campusconnect.campusconnect_auth.model.Program;
import com.campusconnect.campusconnect_auth.repository.AdmissionApplicationRepository;
import com.campusconnect.campusconnect_auth.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionApplicationRepository applicationRepository;
    private final ProgramRepository programRepository;


    public List<AdmissionResponseDTO> getApplicationsForStudent(String email) {
        List<AdmissionApplication> apps = applicationRepository.findByStudentEmail(email);

        return apps.stream().map(app -> new AdmissionResponseDTO(
                app.getId(),
                app.getName(),
                app.getDob(),
                app.getGender(),
                app.getPhoneNumber(),
                app.getCategory(),
                app.getStatus().name(),
                app.getProgramApplied().getName(),
                app.getProgramApplied().getDepartment().getName(),
                app.getCreatedAt(),
                app.getDocumentUrls()
        )).toList();
    }

    public void uploadDocumentsAndSaveUrls(Long applicationId, String studentEmail, MultipartFile[] files) throws IOException {
        AdmissionApplication app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getStudentEmail().equals(studentEmail)) {
            throw new RuntimeException("You are not authorized to update this application.");
        }

        Path folderPath = Paths.get("uploads", "admissions", "application_" + applicationId);
        Files.createDirectories(folderPath);

        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String uniqueName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path fullPath = folderPath.resolve(uniqueName);
            Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);

            urls.add("/files/view/admissions/application_" + applicationId + "/" + uniqueName);
        }

        app.setDocumentUrls(urls);
        applicationRepository.save(app);
    }



    public void applyForAdmission(ApplyAdmissionRequest request, String studentEmail) {
        // Check program exists
        Program program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new RuntimeException("Program not found"));


        AdmissionApplication application = new AdmissionApplication();
        application.setName(request.getName());
        application.setDob(request.getDob());
        application.setGender(request.getGender());
        application.setPhoneNumber(request.getPhoneNumber());
        application.setAddress(request.getAddress());
        application.setNationality(request.getNationality());
        application.setCategory(request.getCategory());
        application.setAadhaarNumber(request.getAadhaarNumber());

        application.setHighSchoolName(request.getHighSchoolName());
        application.setHighSchoolScore(request.getHighSchoolScore());
        application.setIntermediateName(request.getIntermediateName());
        application.setIntermediateScore(request.getIntermediateScore());
        application.setEntranceExamName(request.getEntranceExamName());
        application.setEntranceExamScore(request.getEntranceExamScore());
        application.setYearOfPassing(request.getYearOfPassing());

        application.setProgramApplied(program);
        application.setDocumentUrls(request.getDocumentUrls());
        application.setStatus(AdmissionApplication.Status.PENDING);
        application.setCreatedAt(LocalDateTime.now());
        application.setStudentEmail(studentEmail);

        if (applicationRepository.existsByStudentEmailAndProgramApplied_Id(studentEmail, request.getProgramId())) {
            throw new RuntimeException("You have already applied to this program.");
        }


        applicationRepository.save(application);
    }

    public void updateApplicationDocuments(Long applicationId, String studentEmail, List<String> urls) {
        AdmissionApplication app = applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getStudentEmail().equals(studentEmail)) {
            throw new RuntimeException("You are not authorized to update this application.");
        }

        app.setDocumentUrls(urls);
        applicationRepository.save(app);
    }

}
