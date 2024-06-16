package com.example.demo.dto.plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdatePlanRequest {
    private String plan;
    private String image;
    private Integer checkNum;
    private Boolean isChecked;
    private Boolean isClosed;
    private LocalDateTime date;
}
