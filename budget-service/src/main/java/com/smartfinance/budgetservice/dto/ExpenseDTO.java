package com.smartfinance.budgetservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    private Long id;

    private Long userId;

    private double amount;

    private String category;

    private String month;

    private int year;
}