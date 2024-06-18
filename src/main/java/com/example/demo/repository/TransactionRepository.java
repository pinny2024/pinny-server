package com.example.demo.repository;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser_Id(Long userId);
    List<Transaction> findByUser_IdAndCategory(Long userId, Category category);
    List<Transaction> findByUser_IdAndQuest_Id(Long userId, Long questId);
    List<Transaction> findByCategory(Category category);
    List<Transaction> findByUser_IdAndType(Long userId, Type type);
    List<Transaction> findByUser_IdAndCreatedAtBetween(Long userId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}
