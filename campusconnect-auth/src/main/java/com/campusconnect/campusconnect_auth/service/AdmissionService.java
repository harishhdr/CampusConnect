package com.campusconnect.campusconnect_auth.service;

import com.campusconnect.campusconnect_auth.dto.AdmissionResponseDTO;
import com.campusconnect.campusconnect_auth.dto.ApplyAdmissionRequest;
import com.campusconnect.campusconnect_auth.model.AdmissionApplication;
import com.campusconnect.campusconnect_auth.model.Program;
import com.campusconnect.campusconnect_auth.repository.AdmissionApplicationRepository;
import com.campusconnect.campusconnect_auth.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}
