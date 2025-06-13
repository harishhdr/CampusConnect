package com.campusconnect.campusconnect_auth.service;

import com.campusconnect.campusconnect_auth.dto.CourseDTO;
import com.campusconnect.campusconnect_auth.dto.CourseRegistrationResponse;
import com.campusconnect.campusconnect_auth.dto.RegisterCourseRequest;
import com.campusconnect.campusconnect_auth.model.Course;
import com.campusconnect.campusconnect_auth.model.CourseRegistration;
import com.campusconnect.campusconnect_auth.model.User;
import com.campusconnect.campusconnect_auth.repository.CourseRepository;
import com.campusconnect.campusconnect_auth.repository.CourseRegistrationRepository;
import com.campusconnect.campusconnect_auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository registrationRepository;  // ✅ Add this
    private final UserRepository userRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(course -> new CourseDTO(
                course.getId(),
                course.getCode(),
                course.getTitle(),
                course.getCredits(),
                course.getType().name(),
                course.getDepartment().getName()
        )).toList();
    }

    public void registerCourse(RegisterCourseRequest request, String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Optional: prevent duplicate registration
        boolean alreadyRegistered = registrationRepository.existsByStudentAndCourse(student, course);
        if (alreadyRegistered) {
            throw new RuntimeException("You have already registered for this course.");
        }

        CourseRegistration reg = new CourseRegistration();
        reg.setStudent(student);
        reg.setCourse(course);
        reg.setSemester(request.getSemester());
        reg.setStatus("REGISTERED");

        registrationRepository.save(reg);
    }

    public List<CourseRegistrationResponse> getRegisteredCourses(String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        System.out.println("Fetching registrations for: " + studentEmail);


        return registrationRepository.findByStudent(student).stream().map(reg -> {
            Course course = reg.getCourse();
            return new CourseRegistrationResponse(
                    course.getCode(),
                    course.getTitle(),
                    course.getCredits(),
                    course.getDepartment().getName(),
                    reg.getSemester(),
                    reg.getStatus()
            );
        }).toList();
    }

    public void dropCourse(Long registrationId, String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CourseRegistration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        if (!reg.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("You are not authorized to drop this course.");
        }

        reg.setStatus("DROPPED");
        registrationRepository.save(reg);
    }

    public void reEnrollCourse(Long registrationId, String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CourseRegistration reg = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        if (!reg.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("Not your course registration");
        }

        reg.setStatus("REGISTERED");
        registrationRepository.save(reg);
    }


}
