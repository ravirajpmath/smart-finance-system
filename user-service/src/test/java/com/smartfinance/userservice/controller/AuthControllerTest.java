package com.smartfinance.userservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfinance.userservice.dto.LoginRequest;
import com.smartfinance.userservice.dto.RegisterRequest;
import com.smartfinance.userservice.entity.User;
import com.smartfinance.userservice.service.UserService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_Success() throws Exception {

        RegisterRequest request =
                new RegisterRequest(
                        "Ravi",
                        "ravi@gmail.com",
                        "1234",
                        "USER"
                );

        User user = User.builder()
                .id(1L)
                .name("Ravi")
                .email("ravi@gmail.com")
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        when(userService.registerUser(any(RegisterRequest.class)))
                .thenReturn(user);

        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Ravi"));
    }

    @Test
    void loginUser_Success() throws Exception {

        LoginRequest request =
                new LoginRequest(
                        "ravi@gmail.com",
                        "1234"
                );

        when(userService.loginUser(any(LoginRequest.class)))
                .thenReturn("mocked-jwt-token");

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
                .andExpect(status().isOk())
                .andExpect(content()
                        .string("mocked-jwt-token"));
    }

    @Test
    void getUserByEmail_Success() throws Exception {

        User user = User.builder()
                .id(1L)
                .name("Ravi")
                .email("ravi@gmail.com")
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        when(userService.getUserByEmail("ravi@gmail.com"))
                .thenReturn(user);

        mockMvc.perform(
                get("/api/auth/email/ravi@gmail.com")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email")
                        .value("ravi@gmail.com"));
    }

    @Test
    void getAllUsers_AdminAccess() throws Exception {

        User user = User.builder()
                .id(1L)
                .name("Ravi")
                .email("ravi@gmail.com")
                .role("USER")
                .build();

        when(userService.getAllUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(
                get("/api/auth/admin/users")
                        .header("X-Role", "ADMIN")
        )
                .andExpect(status().isOk());
    }

    @Test
    void getAllUsers_UserAccessDenied() throws Exception {

        mockMvc.perform(
                get("/api/auth/admin/users")
                        .header("X-Role", "USER")
        )
                .andExpect(status().isForbidden())
                .andExpect(content()
                        .string("Access Denied"));
    }
}