package com.example.demo.dto.plan;

import com.example.demo.domain.Plan;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanResponse {
    private Long id;
    private Long userId;
    private String plan;
    private String image;
    private Boolean isChecked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PlanResponse(Plan plan) {
        System.out.println(plan);
        this.id = plan.getId();
        this.userId = plan.getUser().getId();
        this.plan = plan.getPlan();
        this.image = plan.getImage();
        this.isChecked = plan.isChecked(); // Change this line to use isChecked()
        this.createdAt = plan.getCreatedAt();
        this.updatedAt = plan.getUpdatedAt();
    }
}
