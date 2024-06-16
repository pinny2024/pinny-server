package com.example.demo.controller;

import com.example.demo.domain.Plan;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.PlanResponse;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.exception.PlanCheckException;
import com.example.demo.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponse> addPlan(@RequestBody AddPlanRequest request) {
        Plan savedPlan = planService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PlanResponse(savedPlan));
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<List<PlanResponse>> findAllPlans(@PathVariable("user_id") Long userId) {
        List<Plan> plans = planService.findAllPlansByUserId(userId);
        List<PlanResponse> responses = plans.stream()
                .map(PlanResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{user_id}/{id}")
    public ResponseEntity<PlanResponse> findPlan(@PathVariable("user_id") Long userId,
                                                 @PathVariable("id") Long id) {
        Plan plan = planService.findById(id);
        return ResponseEntity.ok()
                .body(new PlanResponse(plan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponse> updatePlan(@PathVariable("id") Long id,
                                                   @RequestBody UpdatePlanRequest request) {
        Plan updatedPlan = planService.update(id, request);
        return ResponseEntity.ok()
                .body(new PlanResponse(updatedPlan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable("id") Long id) {
        planService.delete(id);
        return ResponseEntity.ok()
                .body("id가 " + id + "인 계획이 삭제되었습니다.");
    }

    @PostMapping("/{id}/check")
    public ResponseEntity<PlanResponse> checkPlan(@PathVariable("id") Long id) {
        Plan checkedPlan = planService.checkPlan(id);
        return ResponseEntity.ok()
                .body(new PlanResponse(checkedPlan));
    }

    @ExceptionHandler(PlanCheckException.class)
    public ResponseEntity<String> handlePlanCheckException(PlanCheckException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
