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
    private Integer checkNum;
    private Boolean isChecked;
    private Boolean isClosed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PlanResponse(Plan plan) {
        this.id = plan.getId();
        this.userId = plan.getUser().getId();
        this.plan = plan.getPlan();
        this.image = plan.getImage();
        this.checkNum = plan.getCheckNum();
        this.isChecked = plan.getIsChecked();
        this.isClosed = plan.getIsClosed();
        this.createdAt = plan.getCreatedAt();
        this.updatedAt = plan.getUpdatedAt();
    }
}
