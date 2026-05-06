package com.smartfinance.expenseservice.dto;

import java.time.LocalDate;

import com.smartfinance.expenseservice.entity.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ExpenseRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 1, message = "Amount must be greater than 0")
    private double amount;

    @NotNull(message = "Category is required")
    private Category category;

    private String description;

    @NotNull(message = "Date is required")
    private LocalDate date;

}