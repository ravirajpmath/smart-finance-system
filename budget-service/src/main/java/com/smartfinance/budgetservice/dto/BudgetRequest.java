package com.smartfinance.budgetservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BudgetRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

}