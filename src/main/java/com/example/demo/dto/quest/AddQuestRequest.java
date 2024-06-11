package com.example.demo.dto.quest;

import com.example.demo.domain.Quest;
import com.example.demo.domain.QuestCategory;
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
    private Long questCategoryId;
    private String quest;
    private Integer price;

    public Quest toEntity(User user, QuestCategory category) {
        return Quest.builder()
                .user(user)
                .category(category)
                .quest(quest)
                .price(price)
                .build();
    }
}
