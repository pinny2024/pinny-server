package com.example.demo.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePlanRequest {
    private long planId;
    private long userId;
    private String plan;
    private String image;
    private Boolean isChecked;
    private LocalDateTime date;
}