package com.smartfinance.budgetservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.smartfinance.budgetservice.dto.IncomeDTO;

@FeignClient(name = "INCOME-SERVICE")
public interface IncomeClient {

    @GetMapping("/api/income")
    List<IncomeDTO> getIncome(
            @RequestHeader("X-User") String userEmail
    );
}