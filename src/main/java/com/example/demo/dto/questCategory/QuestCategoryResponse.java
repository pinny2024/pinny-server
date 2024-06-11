package com.example.demo.dto.questCategory;

import com.example.demo.domain.QuestCategory;
import lombok.Getter;

@Getter
public class QuestCategoryResponse {
    private Long questCategoryId;
    private String name;

    public QuestCategoryResponse(QuestCategory questCategory) {
        this.questCategoryId = questCategory.getId();
        this.name = questCategory.getName();
    }
}
