package com.campusconnect.campusconnect_auth.controller.Auth;

import com.campusconnect.campusconnect_auth.dto.AuthResponse;
import com.campusconnect.campusconnect_auth.dto.LoginRequest;
import com.campusconnect.campusconnect_auth.dto.RegisterRequest;
import com.campusconnect.campusconnect_auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getLoggedInUserInfo(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: No user is logged in.");
        }

        String email = authentication.getName();
        return ResponseEntity.ok(authService.getCurrentUser(email));
    }


}
