package com.example.demo.service;

import com.example.demo.domain.QuestCategory;
import com.example.demo.dto.questCategory.AddQuestCategoryRequest;
import com.example.demo.repository.QuestCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestCategoryService {
    private final QuestCategoryRepository questCategoryRepository;

    public QuestCategory save(AddQuestCategoryRequest request) {
        return questCategoryRepository.save(request.toEntity());
    }

    public QuestCategory findById(Long id) {
        return questCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+id));
    }

    public List<QuestCategory> findAll() {
        return questCategoryRepository.findAll();
    }
}
