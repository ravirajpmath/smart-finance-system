package com.smartfinance.incomeservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartfinance.incomeservice.client.UserClient;
import com.smartfinance.incomeservice.entity.Income;
import com.smartfinance.incomeservice.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeService {

    private final IncomeRepository incomeRepository;

    private final UserClient userClient;

    // ✅ GET USER ID
    private Long getUserId(String email) {

        log.info(
                "Fetching userId using Feign Client for email: {}",
                email
        );

        UserDTO user =
                userClient.getUserByEmail(email);

        if (user == null) {

            log.error(
                    "User not found for email: {}",
                    email
            );

            throw new RuntimeException(
                    "User not found"
            );
        }

        return user.getId();
    }

    // ✅ ADD INCOME
    public Income addIncome(
            String email,
            Income income) {

        log.info(
                "Saving income details to database"
        );

        Long userId =
                getUserId(email);

        income.setUserId(userId);

        Income savedIncome =
                incomeRepository.save(income);

        log.info(
                "Income saved successfully"
        );

        return savedIncome;
    }

    // ✅ GET INCOME
    public List<Income> getIncome(
            String email) {

        log.info(
                "Fetching income details from database"
        );

        Long userId =
                getUserId(email);

        return incomeRepository.findByUserId(userId);
    }
}