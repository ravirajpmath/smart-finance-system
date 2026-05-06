package com.smartfinance.expenseservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smartfinance.expenseservice.entity.Expense;
import com.smartfinance.expenseservice.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final RestTemplate restTemplate;

    // ✅ GET USER ID
    private Long getUserId(String email) {

        log.info(
                "Fetching userId from user-service for email: {}",
                email
        );

        String url =
                "http://USER-SERVICE/api/auth/email/" + email;

        UserDTO user =
                restTemplate.getForObject(
                        url,
                        UserDTO.class
                );

        if (user == null) {

            log.error(
                    "User not found for email: {}",
                    email
            );

            throw new RuntimeException(
                    "User not found"
            );
        }

        return user.getId();
    }

    // ✅ ADD EXPENSE
    public Expense addExpense(
            String email,
            Expense expense) {

        log.info(
                "Saving expense details to database"
        );

        Long userId =
                getUserId(email);

        expense.setUserId(userId);

        Expense savedExpense =
                expenseRepository.save(expense);

        log.info(
                "Expense saved successfully"
        );

        return savedExpense;
    }

    // ✅ GET EXPENSES
    public List<Expense> getExpenses(
            String email) {

        log.info(
                "Fetching expense details from database"
        );

        Long userId =
                getUserId(email);

        return expenseRepository.findByUserId(userId);
    }
}