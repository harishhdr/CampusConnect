// RoleRepository.java
package com.campusconnect.campusconnect_auth.repository;

import com.campusconnect.campusconnect_auth.model.Role;
import com.campusconnect.campusconnect_auth.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
