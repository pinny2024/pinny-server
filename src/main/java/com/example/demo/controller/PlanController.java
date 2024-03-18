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

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plans")
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<Plan> addPlan(@RequestBody AddPlanRequest request) {
        Plan savedPlan = planService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPlan);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> findAllPlans() {
        List<PlanResponse> plans = planService.findAll()
                .stream()
                .map(PlanResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(plans);
    }

    @PatchMapping
    public ResponseEntity<Plan> updatePlan(@PathVariable long id,
                                           @RequestBody UpdatePlanRequest request) {
        Plan updatePlan = planService.update(id, request);
        return ResponseEntity.ok()
                .body(updatePlan);
    }
}