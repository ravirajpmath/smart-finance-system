package com.smartfinance.budgetservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {

    private Long id;

    private Long userId;

    private double amount;

    private String month;

    private int year;

    private String source;
}