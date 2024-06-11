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
    private Long userId;
    private String plan;
    private String image;
    private Boolean isChecked;

    public Plan toEntity(User user) {
        return Plan.builder()
                .user(user)
                .plan(plan)
                .image(image)
                .isChecked(isChecked)
                .build();
    }
}