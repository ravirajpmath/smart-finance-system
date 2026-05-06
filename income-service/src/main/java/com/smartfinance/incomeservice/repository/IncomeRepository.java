package com.smartfinance.incomeservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfinance.incomeservice.entity.Income;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findByUserId(Long userId);

}