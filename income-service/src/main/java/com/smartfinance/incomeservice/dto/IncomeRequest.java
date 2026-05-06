package com.smartfinance.incomeservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class IncomeRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount;

    @NotBlank(message = "Month is required")
    private String month;

    @Min(value = 2020, message = "Year must be valid")
    private int year;

    private String source;

}