package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
import com.example.demo.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestResponse {
    private Long questId;
    private Long userId;
    private String quest;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QuestResponse(Quest quest) {
        this.questId = quest.getId();
        this.userId = quest.getUser().getId();
        this.quest = quest.getQuest();
        this.image = quest.getImage();
        this.startTime = quest.getStartTime();
        this.endTime = quest.getEndTime();
        this.createdAt = quest.getCreatedAt();
        this.updatedAt = quest.getUpdatedAt();
    }
}
