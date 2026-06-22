package com.smartfinance.userservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.smartfinance.userservice.config.JwtUtil;
import com.smartfinance.userservice.dto.LoginRequest;
import com.smartfinance.userservice.dto.RegisterRequest;
import com.smartfinance.userservice.entity.User;
import com.smartfinance.userservice.exception.UserAlreadyExistsException;
import com.smartfinance.userservice.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setup() {

        registerRequest = new RegisterRequest();
        registerRequest.setName("Ravi");
        registerRequest.setEmail("ravi@gmail.com");
        registerRequest.setPassword("1234");
        registerRequest.setRole("USER");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("ravi@gmail.com");
        loginRequest.setPassword("1234");

        user = User.builder()
                .id(1L)
                .name("Ravi")
                .email("ravi@gmail.com")
                .password("encodedPassword")
                .role("USER")
                .build();
    }

    @Test
    void registerUser_Success() {

        when(userRepository.findByEmail(
                registerRequest.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode("1234"))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        User savedUser =
                userService.registerUser(registerRequest);

        assertNotNull(savedUser);

        assertEquals(
                "Ravi",
                savedUser.getName()
        );

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void registerUser_EmailAlreadyExists() {

        when(userRepository.findByEmail(
                registerRequest.getEmail()))
                .thenReturn(Optional.of(user));

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.registerUser(registerRequest)
        );

        verify(userRepository, never())
                .save(any(User.class));
    }

    @Test
    void loginUser_Success() {

        when(userRepository.findByEmail(
                loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "1234",
                "encodedPassword"))
                .thenReturn(true);

        when(jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()))
                .thenReturn("mocked-jwt-token");

        String token =
                userService.loginUser(loginRequest);

        assertEquals(
                "mocked-jwt-token",
                token
        );
    }

    @Test
    void loginUser_InvalidPassword() {

        when(userRepository.findByEmail(
                loginRequest.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(
                "1234",
                "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> userService.loginUser(loginRequest)
                );

        assertEquals(
                "Invalid password",
                exception.getMessage()
        );
    }

    @Test
    void getUserByEmail_Success() {

        when(userRepository.findByEmail(
                "ravi@gmail.com"))
                .thenReturn(Optional.of(user));

        User foundUser =
                userService.getUserByEmail(
                        "ravi@gmail.com"
                );

        assertEquals(
                "Ravi",
                foundUser.getName()
        );
    }
}