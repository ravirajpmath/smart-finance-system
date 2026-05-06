package com.smartfinance.budgetservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetResponse {

    private double income;
    private double needs;
    private double wants;
    private double savings;

    private double actualSpending;
    private double remaining;

    private String status;
    private String advice;

    private String topCategory;
    private String categoryAdvice;
}