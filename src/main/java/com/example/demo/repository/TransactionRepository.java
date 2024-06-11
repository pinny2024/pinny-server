package com.example.demo.repository;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCategory(Category category);
    List<Transaction> findByUser_Id(Long userId);
}
