package com.example.demo.repository;

import com.example.demo.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByUser_Id(Long userId);
}
