package com.smartfinance.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.userservice.dto.LoginRequest;
import com.smartfinance.userservice.dto.RegisterRequest;
import com.smartfinance.userservice.entity.User;
import com.smartfinance.userservice.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
}