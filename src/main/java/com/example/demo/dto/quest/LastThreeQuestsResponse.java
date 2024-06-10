package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LastThreeQuestsResponse {
    private Long questId;
    private String quest;
    private Integer price;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LastThreeQuestsResponse(Quest quest) {
        this.questId = quest.getId();
        this.quest = quest.getQuest();
        this.price = quest.getPrice();
        this.image = quest.getImage();
        this.startTime = quest.getStartTime();
        this.endTime = quest.getEndTime();
        this.createdAt = quest.getCreatedAt();
        this.updatedAt = quest.getUpdatedAt();
    }
}
