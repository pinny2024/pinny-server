package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionDTO {
    private BigDecimal amount;
    private String category;
    private String description;
    private String type; // type 필드 추가
}
