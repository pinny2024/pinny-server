package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddQuestRequest {
    private long questId;
    private long userId;
    private String quest;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Quest toEntity() {
        return Quest.builder()
                .userId(userId)
                .quest(quest)
                .image(image)
                .startTime(startTime)
                .build();
    }
}