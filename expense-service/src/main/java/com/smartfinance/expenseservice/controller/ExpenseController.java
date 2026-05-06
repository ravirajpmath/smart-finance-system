package com.smartfinance.expenseservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.expenseservice.entity.Expense;
import com.smartfinance.expenseservice.service.ExpenseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;

    // ✅ ADD EXPENSE
    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @RequestHeader("X-User") String userEmail,
            @RequestBody Expense expense) {

        log.info(
                "Expense creation request received for user: {}",
                userEmail
        );

        return ResponseEntity.ok(
                expenseService.addExpense(userEmail, expense)
        );
    }

    // ✅ GET EXPENSES
    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestHeader("X-User") String userEmail) {

        log.info(
                "Fetching expense details for user: {}",
                userEmail
        );

        return ResponseEntity.ok(
                expenseService.getExpenses(userEmail)
        );
    }
}