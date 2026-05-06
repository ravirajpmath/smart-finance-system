package com.smartfinance.budgetservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smartfinance.budgetservice.dto.BudgetResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {

    private final RestTemplate restTemplate;

    public BudgetResponse calculateBudget(String userEmail) {

        log.info(
                "Starting budget calculation for user: {}",
                userEmail
        );

        String incomeUrl =
                "http://INCOME-SERVICE/api/income";

        String expenseUrl =
                "http://EXPENSE-SERVICE/api/expense";

        HttpHeaders headers =
                new HttpHeaders();

        headers.set("X-User", userEmail);

        HttpEntity<String> entity =
                new HttpEntity<>(headers);

        // ✅ INCOME SERVICE
        log.info(
                "Calling income-service"
        );

        ResponseEntity<Object[]> incomeResponse =
                restTemplate.exchange(
                        incomeUrl,
                        HttpMethod.GET,
                        entity,
                        Object[].class
                );

        double totalIncome = 0;

        if (incomeResponse.getBody() != null) {

            for (Object obj : incomeResponse.getBody()) {

                Map<?, ?> map =
                        (Map<?, ?>) obj;

                totalIncome += Double.parseDouble(
                        map.get("amount").toString()
                );
            }
        }

        // ✅ EXPENSE SERVICE
        log.info(
                "Calling expense-service"
        );

        ResponseEntity<Object[]> expenseResponse =
                restTemplate.exchange(
                        expenseUrl,
                        HttpMethod.GET,
                        entity,
                        Object[].class
                );

        double totalExpenses = 0;

        Map<String, Double> categoryMap =
                new HashMap<>();

        if (expenseResponse.getBody() != null) {

            for (Object obj : expenseResponse.getBody()) {

                Map<?, ?> map =
                        (Map<?, ?>) obj;

                double amount =
                        Double.parseDouble(
                                map.get("amount").toString()
                        );

                String category =
                        map.get("category").toString();

                totalExpenses += amount;

                categoryMap.put(
                        category,
                        categoryMap.getOrDefault(
                                category,
                                0.0
                        ) + amount
                );
            }
        }

        // ✅ TOP CATEGORY
        String topCategory = null;
        double max = 0;

        for (Map.Entry<String, Double> entry :
                categoryMap.entrySet()) {

            if (entry.getValue() > max) {

                max = entry.getValue();
                topCategory = entry.getKey();
            }
        }

        String categoryAdvice =
                (topCategory != null)
                        ? "You spend most on " + topCategory
                        : "No data";

        // ✅ CALCULATIONS
        double needs = totalIncome * 0.5;
        double wants = totalIncome * 0.3;
        double savings = totalIncome * 0.2;

        double remaining =
                totalIncome - totalExpenses;

        String status;
        String advice;

        if (totalExpenses > totalIncome) {

            status = "Overspending";
            advice = "Reduce expenses immediately.";

        } else if (totalExpenses > (needs + wants)) {

            status = "High Spending";
            advice = "Spending too much.";

        } else if (totalExpenses > (needs + wants) * 0.9) {

            status = "Warning";
            advice = "Close to overspending.";

        } else if (remaining < savings) {

            status = "Moderate";
            advice = "Increase savings.";

        } else {

            status = "Good";
            advice = "Great job!";
        }

        log.info(
                "Budget calculation completed successfully"
        );

        return BudgetResponse.builder()
                .income(totalIncome)
                .needs(needs)
                .wants(wants)
                .savings(savings)
                .actualSpending(totalExpenses)
                .remaining(remaining)
                .status(status)
                .advice(advice)
                .topCategory(topCategory)
                .categoryAdvice(categoryAdvice)
                .build();
    }
}