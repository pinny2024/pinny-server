package com.example.demo.service;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsByUserIdAndCategory(Long userId, Category category) {
        return transactionRepository.findByUser_IdAndCategory(userId, category);
    }

    public List<Transaction> getTransactionsByUserIdAndQuestId(Long userId, Long questId) {
        return transactionRepository.findByUser_IdAndQuest_Id(userId, questId);
    }

    public List<Transaction> getTransactionsByCategory(Category category) {
        return transactionRepository.findByCategory(category);
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUser_Id(userId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
