package com.example.demo.repository;

import com.example.demo.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findByCategory(String category);
    List<Income> findByUserId(Long userId);
    List<Income> findByUserIdAndCategory(Long userId, String category);
}
