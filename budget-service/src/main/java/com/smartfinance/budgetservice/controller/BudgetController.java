package com.smartfinance.budgetservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.budgetservice.dto.BudgetResponse;
import com.smartfinance.budgetservice.service.BudgetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetResponse> calculate(
            @RequestHeader("X-User") String userEmail) {

        return ResponseEntity.ok(
                budgetService.calculateBudget(userEmail)
        );
    }
}