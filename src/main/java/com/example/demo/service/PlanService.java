package com.example.demo.service;

import com.example.demo.domain.Plan;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

    //블로그 글 하나 조회( 데이터베이스 id 이용해 글 조회 )
    public Plan findById(Long id){
        return planRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("not found: "+id));
    }

    @Transactional
    public Plan update(Long id, UpdatePlanRequest request){
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