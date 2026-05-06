package com.smartfinance.expenseservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smartfinance.expenseservice.entity.Expense;
import com.smartfinance.expenseservice.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final RestTemplate restTemplate;

    // 🔥 get userId from user-service
    private Long getUserId(String email) {

        String url = "http://localhost:8081/api/auth/email/" + email;

        UserDTO user = restTemplate.getForObject(url, UserDTO.class);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user.getId();
    }

    // ✅ ADD
    public Expense addExpense(String email, Expense expense) {

        Long userId = getUserId(email);

        expense.setUserId(userId);

        return expenseRepository.save(expense);
    }

    // ✅ GET
    public List<Expense> getExpenses(String email) {

        Long userId = getUserId(email);

        return expenseRepository.findByUserId(userId);
    }
}