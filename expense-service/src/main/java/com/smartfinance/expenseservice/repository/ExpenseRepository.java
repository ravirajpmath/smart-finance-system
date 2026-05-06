package com.smartfinance.expenseservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartfinance.expenseservice.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);
}