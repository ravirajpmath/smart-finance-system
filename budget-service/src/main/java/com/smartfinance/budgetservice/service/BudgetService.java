package com.smartfinance.budgetservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.smartfinance.budgetservice.client.ExpenseClient;
import com.smartfinance.budgetservice.client.IncomeClient;
import com.smartfinance.budgetservice.dto.BudgetResponse;
import com.smartfinance.budgetservice.dto.ExpenseDTO;
import com.smartfinance.budgetservice.dto.IncomeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetService {

    private final IncomeClient incomeClient;

    private final ExpenseClient expenseClient;

    public BudgetResponse calculateBudget(
            String userEmail) {

        log.info(
                "Starting budget calculation for user: {}",
                userEmail
        );

        // ✅ FETCH INCOME
        log.info(
                "Fetching income details using Feign Client"
        );

        List<IncomeDTO> incomes =
                incomeClient.getIncome(userEmail);

        double totalIncome = 0;

        for (IncomeDTO income : incomes) {

            totalIncome += income.getAmount();
        }

        // ✅ FETCH EXPENSES
        log.info(
                "Fetching expense details using Feign Client"
        );

        List<ExpenseDTO> expenses =
                expenseClient.getExpenses(userEmail);

        double totalExpenses = 0;

        Map<String, Double> categoryMap =
                new HashMap<>();

        for (ExpenseDTO expense : expenses) {

            totalExpenses += expense.getAmount();

            categoryMap.put(
                    expense.getCategory(),
                    categoryMap.getOrDefault(
                            expense.getCategory(),
                            0.0
                    ) + expense.getAmount()
            );
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
                .categoryBreakdown(categoryMap)
                .build();
    }
}