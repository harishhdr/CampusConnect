package com.campusconnect.campusconnect_auth.repository;

import com.campusconnect.campusconnect_auth.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
