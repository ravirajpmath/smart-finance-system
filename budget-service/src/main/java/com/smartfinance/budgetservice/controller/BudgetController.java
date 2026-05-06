package com.smartfinance.budgetservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.budgetservice.dto.BudgetResponse;
import com.smartfinance.budgetservice.service.BudgetService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
@Slf4j
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetResponse> calculate(
            @RequestHeader("X-User") String userEmail) {

        log.info(
                "Budget calculation request received for user: {}",
                userEmail
        );

        return ResponseEntity.ok(
                budgetService.calculateBudget(userEmail)
        );
    }
}