package com.smartfinance.budgetservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.smartfinance.budgetservice.dto.ExpenseDTO;

@FeignClient(name = "EXPENSE-SERVICE")
public interface ExpenseClient {

    @GetMapping("/api/expense")
    List<ExpenseDTO> getExpenses(
            @RequestHeader("X-User") String userEmail
    );
}