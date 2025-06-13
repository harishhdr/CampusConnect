package com.campusconnect.campusconnect_auth.service;

import com.campusconnect.campusconnect_auth.dto.LoginRequest;
import com.campusconnect.campusconnect_auth.dto.RegisterRequest;
import com.campusconnect.campusconnect_auth.dto.AuthResponse;
import com.campusconnect.campusconnect_auth.dto.UserProfileResponse;
import com.campusconnect.campusconnect_auth.model.Role;
import com.campusconnect.campusconnect_auth.model.User;
import com.campusconnect.campusconnect_auth.repository.RoleRepository;
import com.campusconnect.campusconnect_auth.repository.UserRepository;
import com.campusconnect.campusconnect_auth.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (request.getRole() == null || request.getRole().isBlank()) {
            throw new RuntimeException("Role is required during registration");
        }

        Role.RoleName roleName = Role.RoleName.valueOf(request.getRole().toUpperCase());
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Invalid role: " + request.getRole()));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return new AuthResponse(token, "User registered successfully");
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return new AuthResponse(token, "Login successful");
    }

    public UserProfileResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<String> roleNames = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        return new UserProfileResponse(user.getId(), user.getUsername(), user.getEmail(), roleNames);
    }



}
