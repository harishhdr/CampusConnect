package com.campusconnect.campusconnect_auth.repository;

import com.campusconnect.campusconnect_auth.model.Course;
import com.campusconnect.campusconnect_auth.model.CourseRegistration;
import com.campusconnect.campusconnect_auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long> {
    List<CourseRegistration> findByStudent(User student);

    boolean existsByStudentAndCourse(User student, Course course); // ✅ Add this
}
