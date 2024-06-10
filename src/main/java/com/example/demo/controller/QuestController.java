package com.example.demo.controller;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Quest;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.dto.quest.AddQuestRequest;
import com.example.demo.dto.quest.QuestResponse;
import com.example.demo.dto.quest.UpdateQuestRequest;
import com.example.demo.service.PlanService;
import com.example.demo.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quests")
public class QuestController {
    private final QuestService questService;

    @PostMapping
    public ResponseEntity<QuestResponse> addQuest(@RequestBody AddQuestRequest request) {
        Quest savedQuest = questService.save(request);

//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "id가 "+savedQuest.getQuestId()+"인 퀘스트가 생성되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new QuestResponse(savedQuest));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<QuestResponse>> findAllQuests(@PathVariable("user_id") Long userId) {
        List<Quest> quests = questService.findAllByUserId(userId);
        List<QuestResponse> responses = quests.stream()
                .map(QuestResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{user_id}/{id}")
    public ResponseEntity<QuestResponse> findQuest(@PathVariable("user_id") Long userId,
                                                   @PathVariable("id") Long id) {
        Quest quest = questService.findById(id);

        return ResponseEntity.ok()
                .body(new QuestResponse(quest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestResponse> updateQuest(@PathVariable("id") Long id,
                                             @RequestBody UpdateQuestRequest request) {
        Quest updateQuest = questService.update(id, request);

        return ResponseEntity.ok()
                .body(new QuestResponse(updateQuest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuest(@PathVariable("id") Long id) {
        questService.delete(id);

        return ResponseEntity.ok()
                .body("id가 "+id+"인 계획이 삭제되었습니다.");
    }
}
