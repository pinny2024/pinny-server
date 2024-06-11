package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
import com.example.demo.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestResponse {
    private Long questId;
    private Long userId;
    private Long questCategoryId;
    private String quest;
    private Integer price;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public QuestResponse(Quest quest) {
        this.questId = quest.getId();
        this.userId = quest.getUser().getId();
        this.questCategoryId = quest.getCategory().getId();
        this.quest = quest.getQuest();
        this.price = quest.getPrice();
        this.startTime = quest.getStartTime();
        this.endTime = quest.getEndTime();
        this.createdAt = quest.getCreatedAt();
        this.updatedAt = quest.getUpdatedAt();
    }
}
