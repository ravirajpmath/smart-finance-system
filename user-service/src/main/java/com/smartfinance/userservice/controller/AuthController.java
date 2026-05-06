package com.smartfinance.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.userservice.dto.LoginRequest;
import com.smartfinance.userservice.dto.RegisterRequest;
import com.smartfinance.userservice.entity.User;
import com.smartfinance.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestBody RegisterRequest request) {

        log.info(
                "User registration request received for email: {}",
                request.getEmail()
        );

        return ResponseEntity.ok(
                userService.registerUser(request)
        );
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(
            @RequestBody LoginRequest request) {

        log.info(
                "Login request received for email: {}",
                request.getEmail()
        );

        String token =
                userService.loginUser(request);

        return ResponseEntity.ok(token);
    }

    // ✅ GET USER BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(
            @PathVariable String email) {

        log.info(
                "Fetching user details for email: {}",
                email
        );

        return ResponseEntity.ok(
                userService.getUserByEmail(email)
        );
    }
    
    // ✅ ADMIN - GET ALL USERS
    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers(
            @RequestHeader("X-Role") String role) {

        log.info(
                "Admin endpoint accessed for fetching all users"
        );

        // ❌ ROLE CHECK
        if (!role.equals("ADMIN")) {

            log.warn(
                    "Unauthorized access attempt"
            );

            return ResponseEntity.status(403)
                    .body("Access Denied");
        }

        return ResponseEntity.ok(
                userService.getAllUsers()
        );
    }

    // ✅ ADMIN - DELETE USER
    @DeleteMapping("/admin/users/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id,
            @RequestHeader("X-Role") String role) {

        log.info(
                "Admin endpoint accessed for deleting user"
        );

        // ❌ ROLE CHECK
        if (!role.equals("ADMIN")) {

            log.warn(
                    "Unauthorized delete attempt"
            );

            return ResponseEntity.status(403)
                    .body("Access Denied");
        }

        userService.deleteUser(id);

        return ResponseEntity.ok(
                "User deleted successfully"
        );
    }
}