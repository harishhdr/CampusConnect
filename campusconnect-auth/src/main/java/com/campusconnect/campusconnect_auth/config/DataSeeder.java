package com.campusconnect.campusconnect_auth.config;

import com.campusconnect.campusconnect_auth.model.Role;
import com.campusconnect.campusconnect_auth.model.Role.RoleName;
import com.campusconnect.campusconnect_auth.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void seedRoles() {
        Arrays.stream(RoleName.values()).forEach(roleName -> {
            if (roleRepository.findByName(roleName).isEmpty()) {
                roleRepository.save(new Role(null, roleName));
                System.out.println("✅ Seeded role: " + roleName);
            }
        });
    }
}
