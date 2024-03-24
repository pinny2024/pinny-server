package com.example.demo.service;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Quest;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.dto.quest.AddQuestRequest;
import com.example.demo.dto.quest.UpdateQuestRequest;
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

    public Quest findById(Long id){
        return questRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));
    }

    @Transactional
    public Quest update(Long id, UpdateQuestRequest request){
        Quest quest = questRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found "+id));

        quest.update(request.getQuestId(),
            request.getUserId(),
            request.getQuest(),
            request.getImage(),
            request.getStartTime(),
            request.getEndTime());

        return quest;
    }

    public void delete(Long id) {
        questRepository.deleteById(id);
    }
}
