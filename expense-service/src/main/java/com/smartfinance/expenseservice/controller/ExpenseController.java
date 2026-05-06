package com.smartfinance.expenseservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smartfinance.expenseservice.entity.Expense;
import com.smartfinance.expenseservice.service.ExpenseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> addExpense(
            @RequestHeader("X-User") String userEmail,
            @RequestBody Expense expense) {

        return ResponseEntity.ok(
                expenseService.addExpense(userEmail, expense)
        );
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses(
            @RequestHeader("X-User") String userEmail) {

        return ResponseEntity.ok(
                expenseService.getExpenses(userEmail)
        );
    }
}