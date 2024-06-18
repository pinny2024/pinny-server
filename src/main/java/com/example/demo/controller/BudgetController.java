package com.example.demo.controller;

import com.example.demo.domain.Budget;
import com.example.demo.dto.BudgetDTO;
import com.example.demo.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budget/{userId}")
public class BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<String> createBudget(@PathVariable("userId") Long userId, @RequestBody BudgetDTO budgetDTO) {
        Budget createdBudget = budgetService.createBudget(userId, budgetDTO.getTotalBudget());
        String message = String.format("%d원으로 설정하셨습니다.", createdBudget.getTotalBudget());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateBudget(@PathVariable("userId") Long userId, @RequestBody BudgetDTO budgetDTO) {
        try {
            Budget updatedBudget = budgetService.updateTotalBudget(userId, budgetDTO.getTotalBudget());
            String message = String.format("%d원으로 예산을 수정하셨습니다.", updatedBudget.getTotalBudget());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<String> getBudget(@PathVariable("userId") Long userId) {
        Budget budget = budgetService.getBudgetByUserId(userId);
        String message = String.format("남은금액 %d원 남았습니다.", budget.getRemainingBudget());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
