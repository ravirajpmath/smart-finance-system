package com.smartfinance.incomeservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "income")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String month;

    @Column(nullable = false)
    private int year;

    private String source;

    private LocalDateTime createdAt;

}