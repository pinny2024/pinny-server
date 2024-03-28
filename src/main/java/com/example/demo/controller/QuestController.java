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
//@RequestMapping("/quests")
public class QuestController {
    private final QuestService questService;

    @PostMapping("/quests")
    public ResponseEntity<Map<String, Object>> addQuest(@RequestBody AddQuestRequest request) {
        Quest savedQuest = questService.save(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "id가 "+savedQuest.getQuestId()+"인 퀘스트가 생성되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
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
    public ResponseEntity<Map<String, Object>> updateQuest(@PathVariable("id") Long id,
                                           @RequestBody UpdateQuestRequest request) {
        Quest updateQuest = questService.update(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "id가 "+id+"인 계획이 수정되었습니다.");

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/quests/{id}")
    public ResponseEntity<Map<String, Object>> deleteQuest(@PathVariable("id") Long id) {
        questService.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "id가 "+id+"인 계획이 삭제되었습니다.");

        return ResponseEntity.ok()
                .body(response);
    }
}
