package com.smartfinance.incomeservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.incomeservice.entity.Income;
import com.smartfinance.incomeservice.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    // ✅ ADD INCOME
    @PostMapping
    public ResponseEntity<Income> addIncome(
            @RequestHeader("X-User") String userEmail,
            @RequestBody Income income) {

        System.out.println("Incoming request: " + income);

        return ResponseEntity.ok(
                incomeService.addIncome(userEmail, income)
        );
    }

    // ✅ GET INCOME
    @GetMapping
    public ResponseEntity<List<Income>> getIncome(
            @RequestHeader("X-User") String userEmail) {

        return ResponseEntity.ok(
                incomeService.getIncome(userEmail)
        );
    }
}