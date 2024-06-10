package com.example.demo.repository;

import com.example.demo.domain.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {
    List<Expenditure> findByCategory(String category);
    List<Expenditure> findByUserId(Long userId);
    List<Expenditure> findByUserIdAndCategory(Long userId, String category);
}
