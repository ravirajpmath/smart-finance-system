package com.smartfinance.incomeservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.smartfinance.incomeservice.entity.Income;
import com.smartfinance.incomeservice.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final RestTemplate restTemplate;

    // 🔥 get userId from user-service
    private Long getUserId(String email) {

        String url = "http://localhost:8081/api/auth/email/" + email;

        UserDTO user = restTemplate.getForObject(url, UserDTO.class);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user.getId();
    }

    // ✅ ADD INCOME
    public Income addIncome(String email, Income income) {

        Long userId = getUserId(email);

        income.setUserId(userId);

        return incomeRepository.save(income);
    }

    // ✅ GET INCOME
    public List<Income> getIncome(String email) {

        Long userId = getUserId(email);

        return incomeRepository.findByUserId(userId);
    }
}