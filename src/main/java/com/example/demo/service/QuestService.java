package com.example.demo.service;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Quest;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.dto.quest.AddQuestRequest;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.QuestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestService {
    private final QuestRepository questRepository;

    public Quest save(AddQuestRequest request) {
        return questRepository.save(request.toEntity());
    }

    public List<Quest> findAll() {
        return questRepository.findAll();
    }
}
