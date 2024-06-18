package com.example.demo.controller;

import com.example.demo.domain.Budget;
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
    public ResponseEntity<Budget> createBudget(@PathVariable("userId") Long userId, @RequestParam Integer totalBudget) {
        Budget createdBudget = budgetService.createBudget(userId, totalBudget);
        return new ResponseEntity<>(createdBudget, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Budget> getBudget(@PathVariable("userId") Long userId) {
        Budget budget = budgetService.getBudgetByUserId(userId);
        return new ResponseEntity<>(budget, HttpStatus.OK);
    }
}
