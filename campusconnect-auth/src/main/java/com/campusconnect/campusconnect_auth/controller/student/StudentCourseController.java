package com.campusconnect.campusconnect_auth.controller.student;

import com.campusconnect.campusconnect_auth.dto.RegisterCourseRequest;
import com.campusconnect.campusconnect_auth.service.CourseService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student/courses")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentCourseController {

    private final CourseService courseService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCourse(@RequestBody RegisterCourseRequest request,
                                                 Authentication authentication) {
        String email = authentication.getName();
        courseService.registerCourse(request, email);
        return ResponseEntity.ok("✅ Course registered successfully");
    }

    @GetMapping("/my-registrations")
    public ResponseEntity<?> getMyRegisteredCourses(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(courseService.getRegisteredCourses(email));
    }

    @PatchMapping("/drop/{registrationId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> dropRegisteredCourse(@PathVariable Long registrationId,
                                                       Authentication authentication) {
        String email = authentication.getName();
        courseService.dropCourse(registrationId, email);
        return ResponseEntity.ok("❌ Course dropped successfully.");
    }

    @PatchMapping("/re-enroll/{registrationId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> reEnrollCourse(@PathVariable Long registrationId,
                                                 Authentication authentication) {
        String email = authentication.getName();
        courseService.reEnrollCourse(registrationId, email);
        return ResponseEntity.ok("🔁 Re-enrolled in course.");
    }



}
