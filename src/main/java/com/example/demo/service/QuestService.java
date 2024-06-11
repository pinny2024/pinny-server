package com.example.demo.service;

import com.example.demo.domain.Quest;
import com.example.demo.domain.User;
import com.example.demo.dto.quest.AddQuestRequest;
import com.example.demo.dto.quest.LastThreeQuestsResponse;
import com.example.demo.dto.quest.UpdateQuestRequest;
import com.example.demo.repository.QuestRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestService {
    @Autowired
    private final QuestRepository questRepository;
    @Autowired
    private final UserRepository userRepository;

    public Quest save(AddQuestRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));
        Quest quest = request.toEntity(user);
        return questRepository.save(quest);
    }

    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    public Quest findById(Long id){
        return questRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));
    }

    public List<Quest> findAllByUserId(Long userId) {
        return questRepository.findByUserId(userId);
    }

    @Transactional
    public Quest update(Long id, UpdateQuestRequest request){
        Quest quest = questRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found "+id));

        quest.update(
                request.getQuest(),
                request.getPrice(),
                request.getEndTime());
        return quest;
    }

    public void delete(Long id) {
        questRepository.deleteById(id);
    }

    public List<LastThreeQuestsResponse> getPreviousQuests(Long userId) {
        List<Quest> quests = questRepository.findTopByUserIdOrderByStartTimeDesc(userId);
        // 가장 최근의 1개는 제외하고 최대 3개 가져오기
        List<Quest> subList = quests.size() > 1 ? quests.subList(1, Math.min(4, quests.size())) : List.of();
        return subList.stream().map(quest -> new LastThreeQuestsResponse(quest))
                .collect(Collectors.toList());
    }
}
