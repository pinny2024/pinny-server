package com.example.demo.controller;

import com.example.demo.domain.Plan;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<Plan> addPlan(@RequestBody AddPlanRequest request) {
        Plan savedPlan = planService.save(request);

//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "id가 "+savedPlan.getPlanId()+"인 새 계획이 추가되었습니다.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPlan);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<PlanResponse>> findAllPlans(@PathVariable("user_id") Long userId) {
        List<PlanResponse> plans = planService.findAllPlansByUserId(userId)
                .stream()
                .map(PlanResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(plans);
    }

    // GET 요청이 오면 해당 id의 편지글 조회
    @GetMapping("/{user_id}/{id}")
    public ResponseEntity<PlanResponse> findPlan(@PathVariable("user_id") Long userId,
                                                 @PathVariable("id") Long id) {
        Plan plan = planService.findById(id);
        if ( plan.getPlanId() == null ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (!plan.getUserId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok()
                .body(new PlanResponse(plan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable("id") Long id,
                                           @RequestBody UpdatePlanRequest request) {
        Plan updatedPlan = planService.update(id, request);

//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "id가 "+id+"인 계획이 수정되었습니다.");

        return ResponseEntity.ok()
                .body(updatedPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePlan(@PathVariable("id") Long id) {
        planService.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "id가 "+id+"인 계획이 삭제되었습니다.");

        return ResponseEntity.ok()
                .body(response);
    }
}