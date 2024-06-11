package com.example.demo.service;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getTransactionsByCategory(Category category) {
        List<Transaction> transactions = transactionRepository.findByCategory(category);
        if (transactions.isEmpty()) {
            throw new NoSuchElementException("해당 카테고리에 대한 정보가 없습니다.");
        }
        return transactions;
    }
}
