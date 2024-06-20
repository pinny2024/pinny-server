package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private BigDecimal amount;
    private String category;
    private String description;
    private String type;

}
