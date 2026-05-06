package com.smartfinance.expenseservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.smartfinance.expenseservice.service.UserDTO;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/api/auth/email/{email}")
    UserDTO getUserByEmail(
            @PathVariable String email
    );
}