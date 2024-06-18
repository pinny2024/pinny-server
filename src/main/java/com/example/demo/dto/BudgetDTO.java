package com.example.demo.dto;

import lombok.Data;

@Data
public class BudgetDTO {
    private Long userId;
    private Integer totalBudget;
    private Integer remainingBudget;
}
