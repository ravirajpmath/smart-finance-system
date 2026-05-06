package com.smartfinance.userservice.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartfinance.userservice.config.JwtUtil;
import com.smartfinance.userservice.dto.LoginRequest;
import com.smartfinance.userservice.dto.RegisterRequest;
import com.smartfinance.userservice.entity.User;
import com.smartfinance.userservice.exception.UserAlreadyExistsException;
import com.smartfinance.userservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ REGISTER USER
    public User registerUser(RegisterRequest request) {

        log.info("Creating new user account");

        if(userRepository.findByEmail(
                request.getEmail()).isPresent()) {

            log.warn(
                    "User already exists with email: {}",
                    request.getEmail()
            );

            throw new UserAlreadyExistsException(
                    "Email already registered"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(request.getRole())
                .createdAt(LocalDateTime.now())
                .build();

        log.info("Saving user to database");

        return userRepository.save(user);
    }

    // ✅ LOGIN USER
    public String loginUser(LoginRequest request) {

        log.info("Authenticating user");

        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(() -> {

            log.error(
                    "User not found with email: {}",
                    request.getEmail()
            );

            return new RuntimeException(
                    "User not found"
            );
        });

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            log.error(
                    "Invalid password for email: {}",
                    request.getEmail()
            );

            throw new RuntimeException(
                    "Invalid password"
            );
        }

        log.info(
                "JWT token generated successfully"
        );

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );
    }

    // ✅ GET USER BY EMAIL
    public User getUserByEmail(String email) {

        log.info(
                "Fetching user from database"
        );

        return userRepository.findByEmail(email)
                .orElseThrow(() -> {

                    log.error(
                            "User not found with email: {}",
                            email
                    );

                    return new RuntimeException(
                            "User not found"
                    );
                });
    }
    
    // ✅ GET ALL USERS (ADMIN ONLY)
    public List<User> getAllUsers() {

        log.info(
                "Fetching all users from database"
        );

        return userRepository.findAll();
    }

    // ✅ DELETE USER (ADMIN ONLY)
    public void deleteUser(Long id) {

        log.info(
                "Deleting user with id: {}",
                id
        );

        userRepository.deleteById(id);

        log.info(
                "User deleted successfully"
        );
    }
}