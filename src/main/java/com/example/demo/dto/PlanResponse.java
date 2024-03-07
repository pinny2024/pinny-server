package com.example.demo.dto;

import com.example.demo.domain.Plan;
import com.example.demo.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class PlanResponse {
    private long planId;
    private long userId;
    private String plan;
    private String image;
    private Boolean isChecked;
    private LocalDateTime date;

    public PlanResponse(Plan plan) {
        this.planId = plan.getPlanId();
        this.userId = plan.getUserId();
        this.plan = plan.getPlan();
        this.image = plan.getImage();
        this.isChecked = plan.getIsChecked();
        this.date = plan.getDate();
    }
}