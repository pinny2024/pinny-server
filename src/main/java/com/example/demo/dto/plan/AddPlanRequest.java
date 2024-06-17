package com.example.demo.dto.plan;

import com.example.demo.domain.Plan;
import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddPlanRequest {
    private Long userId;
    private String plan;
    private String image;

    public Plan toEntity(User user) {
        return Plan.builder()
                .user(user)
                .plan(plan)
                .image(image)
                .checkNum(0)          // 기본 값 설정
                .isChecked(false)     // 기본 값 설정
                .isClosed(false)      // 기본 값 설정
                .build();
    }
}
