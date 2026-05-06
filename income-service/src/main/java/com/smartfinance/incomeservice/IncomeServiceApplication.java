package com.smartfinance.incomeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class IncomeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncomeServiceApplication.class, args);
	}

}
