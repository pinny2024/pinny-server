package com.example.demo.controller;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import com.example.demo.dto.CategoryNotFoundException;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        transaction.setCreatedAt(LocalDateTime.now()); // 현재 시간 설정
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping("/type")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions().stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory().name());
                    dto.setDescription(transaction.getDescription());
                    dto.setType(transaction.getType().name());
                    dto.setCreatedAt(transaction.getCreatedAt()); // createdAt 설정
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getTransactionsByCategory(@PathVariable String category) {
        if (!Category.getAllCategories().contains(category)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 카테고리 값입니다.");
        }

        Category cat = Category.valueOf(category);
        List<Transaction> transactions = transactionService.getTransactionsByCategory(cat);

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 카테고리에 대한 값이 없습니다.");
        }

        List<TransactionDTO> dtos = transactions.stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory().name());
                    dto.setDescription(transaction.getDescription());
                    dto.setType(transaction.getType().name());
                    dto.setCreatedAt(transaction.getCreatedAt()); // createdAt 설정
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


}
