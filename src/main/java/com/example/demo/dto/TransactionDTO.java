package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime; // LocalDateTime 임포트 추가

@Data
public class TransactionDTO {
    private BigDecimal amount;
    private String category;
    private String description;
    private String type;
    private LocalDateTime createdAt;
}
