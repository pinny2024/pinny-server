package com.example.demo.dto.quest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateQuestRequest {
    private Long questId;
    private Long userId;
    private String quest;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

}
