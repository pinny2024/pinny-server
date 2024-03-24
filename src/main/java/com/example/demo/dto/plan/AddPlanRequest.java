package com.example.demo.dto.plan;

import com.example.demo.domain.Plan;
import com.example.demo.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddPlanRequest {
    private Long planId;
    private Long userId;
    private String plan;
    private String image;
    private Boolean isChecked;
    private LocalDateTime date;

    public Plan toEntity() {
        return Plan.builder()
                .planId(planId)
                .userId(userId)
                .plan(plan)
                .image(image)
                .isChecked(isChecked)
                .date(date)
                .build();
    }
}