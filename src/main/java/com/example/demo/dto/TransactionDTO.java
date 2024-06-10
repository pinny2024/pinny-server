package com.example.demo.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private int money;
    private String category;
    private String content;
    private LocalDateTime createdAt;
    private String type;
}
