package com.example.demo.dto.questCategory;

import com.example.demo.domain.QuestCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddQuestCategoryRequest {
    private String name;

    public QuestCategory toEntity() {
        return QuestCategory.builder()
                .name(name)
                .build();
    }
}
