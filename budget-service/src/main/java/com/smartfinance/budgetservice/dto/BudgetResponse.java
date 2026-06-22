package com.smartfinance.budgetservice.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetResponse {

    // TOTALS

    private double income;

    private double needs;

    private double wants;

    private double savings;

    private double actualSpending;

    private double remaining;

    // STATUS

    private String status;

    private String advice;

    // CATEGORY ANALYSIS

    private String topCategory;

    private String categoryAdvice;

    // ✅ NEW

    private Map<String, Double> categoryBreakdown;
}