package com.example.demo.service;

import com.example.demo.domain.Budget;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.Type;
import com.example.demo.domain.User;
import com.example.demo.repository.BudgetRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final TransactionService transactionService;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository, TransactionService transactionService) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.transactionService = transactionService;
    }

    public Budget createBudget(Long userId, Integer totalBudget) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Budget budget = Budget.builder()
                .user(user)
                .totalBudget(totalBudget)
                .remainingBudget(totalBudget)
                .build();
        return budgetRepository.save(budget);
    }

    public Budget updateTotalBudget(Long userId, Integer totalBudget) {
        Budget budget = budgetRepository.findByUser_Id(userId);
        if (budget == null) {
            throw new IllegalArgumentException("Budget not found");
        }
        if (budget.getTotalBudget().equals(totalBudget)) {
            throw new IllegalArgumentException("새로운 예산 금액이 기존 금액과 동일합니다.");
        }
        budget.setTotalBudget(totalBudget);
        budget.setRemainingBudget(totalBudget);
        return budgetRepository.save(budget);
    }

    public Budget updateRemainingBudget(Long userId) {
        Budget budget = budgetRepository.findByUser_Id(userId);
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndType(userId, Type.지출);
        int totalExpenditure = transactions.stream().mapToInt(t -> t.getAmount().intValue()).sum();
        budget.setRemainingBudget(budget.getTotalBudget() - totalExpenditure);
        return budgetRepository.save(budget);
    }

    public Budget getBudgetByUserId(Long userId) {
        Budget budget = budgetRepository.findByUser_Id(userId);
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndType(userId, Type.지출);
        int totalExpenditure = transactions.stream().mapToInt(t -> t.getAmount().intValue()).sum();
        budget.setRemainingBudget(budget.getTotalBudget() - totalExpenditure);
        return budget;
    }
}
