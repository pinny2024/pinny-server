package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
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
public class AddQuestRequest {
    private Long userId;
    private String quest;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Quest toEntity(User user) {
        return Quest.builder()
                .user(user)
                .quest(quest)
                .image(image)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
