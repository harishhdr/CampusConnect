package com.campusconnect.campusconnect_auth.repository;

import com.campusconnect.campusconnect_auth.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
