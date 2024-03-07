package com.example.demo.service;

import com.example.demo.domain.Plan;
import com.example.demo.dto.AddPlanRequest;
import com.example.demo.dto.UpdatePlanRequest;
import com.example.demo.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public Plan save(AddPlanRequest request) {
        return planRepository.save(request.toEntity());
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Transactional
    public Plan update(long id, UpdatePlanRequest request){
        Plan plan = planRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found "+id));

        plan.update(request.getPlanId(),
                request.getUserId(),
                request.getPlan(),
                request.getImage(),
                request.getIsChecked(),
                request.getDate());

        return plan;
    }
}