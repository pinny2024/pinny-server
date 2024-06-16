package com.example.demo.service;

import com.example.demo.domain.Plan;
import com.example.demo.domain.User;
import com.example.demo.dto.plan.AddPlanRequest;
import com.example.demo.dto.plan.UpdatePlanRequest;
import com.example.demo.exception.PlanCheckException;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {

    @Autowired
    private final PlanRepository planRepository;
    @Autowired
    private final UserRepository userRepository;

    public Plan save(AddPlanRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + request.getUserId()));
        Plan plan = request.toEntity(user);
        return planRepository.save(plan);
    }

    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    public Plan findById(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<Plan> findAllPlansByUserId(Long userId) {
        return planRepository.findByUserId(userId);
    }

    @Transactional
    public Plan update(Long id, UpdatePlanRequest request) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        plan.update(
                request.getPlan(),
                request.getImage(),
                request.getCheckNum(),
                request.getIsChecked(),
                request.getIsClosed()
        );

        return plan;
    }

    public void delete(Long id) {
        planRepository.deleteById(id);
    }

    @Transactional
    public Plan checkPlan(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        if (plan.getCreatedAt().isBefore(LocalDateTime.now().minusWeeks(1))) {
            plan.setIsClosed(true);
        } else {
            try {
                plan.check();
            } catch (IllegalStateException e) {
                throw new PlanCheckException("Unable to check the plan: " + e.getMessage());
            }
        }

        return plan;
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    @Transactional
    public void resetIsCheckedDaily() {
        List<Plan> plans = planRepository.findAll();
        for (Plan plan : plans) {
            if (!plan.getIsClosed() && plan.getIsChecked()) {
                plan.setIsChecked(false);
                planRepository.save(plan); // 변경 사항 저장
            }
        }
    }
}
