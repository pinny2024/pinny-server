package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestResponse {
    private long questId;
    private long userId;
    private String quest;
    private String image;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public QuestResponse(Quest quest) {
        this.questId = quest.getQuestId();
        this.userId = quest.getUserId();
        this.quest = quest.getQuest();
        this.image = quest.getImage();
        this.startTime = quest.getStartTime();
        this.endTime = quest.getEndTime();
    }
}
