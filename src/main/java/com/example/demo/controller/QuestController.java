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

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/quests")
public class QuestController {
    private final QuestService questService;

    @PostMapping("/quests")
    public ResponseEntity<Quest> addQuest(@RequestBody AddQuestRequest request) {
        Quest savedQuest = questService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedQuest);
    }

    @GetMapping("/quests")
    public ResponseEntity<List<QuestResponse>> findAllQuests() {
        List<QuestResponse> quests = questService.findAll()
                .stream()
                .map(QuestResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(quests);
    }

    @GetMapping("/quests/{id}")
    public ResponseEntity<QuestResponse> findQuest(@PathVariable("id") Long id) {
        Quest quest = questService.findById(id);

        return ResponseEntity.ok()
                .body(new QuestResponse(quest));
    }

    @PutMapping("/quests/{id}")
    public ResponseEntity<Quest> updateQuest(@PathVariable("id") Long id,
                                           @RequestBody UpdateQuestRequest request) {
        Quest updateQuest = questService.update(id, request);
        return ResponseEntity.ok()
                .body(updateQuest);
    }

    @DeleteMapping("/quests/{id}")
    public ResponseEntity<Void> deleteQuest(@PathVariable("id") Long id) {
        questService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
