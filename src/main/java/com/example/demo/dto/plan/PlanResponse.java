package com.example.demo.dto.plan;

import com.example.demo.domain.Plan;
import com.example.demo.domain.User;
import com.example.demo.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class PlanResponse {
    private Long id;
    private Long userId;
    private String plan;
    private String image;
    private Boolean isChecked;
    private LocalDateTime date;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PlanResponse(Plan plan) {
        this.id = plan.getId();
        this.userId = plan.getUser().getId();
        this.plan = plan.getPlan();
        this.image = plan.getImage();
        this.isChecked = plan.getIsChecked();
        this.date = plan.getDate();
        this.createdAt = plan.getCreatedAt();
        this.updatedAt = plan.getUpdatedAt();
    }
}