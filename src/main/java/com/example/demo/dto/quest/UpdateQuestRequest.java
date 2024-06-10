package com.example.demo.dto.quest;

import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateQuestRequest {
    private String quest;
    private Integer price;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
