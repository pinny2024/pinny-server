package com.example.demo.controller;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.User;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions/{userId}")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;

    @Autowired
    public TransactionController(TransactionService transactionService, UserRepository userRepository) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("userId") Long userId, @RequestBody Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        transaction.setUser(user);
        transaction.setCreatedAt(LocalDateTime.now());

        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(savedTransaction.getAmount());
        dto.setCategory(savedTransaction.getCategory().name());
        dto.setDescription(savedTransaction.getDescription());
        dto.setType(savedTransaction.getType().name());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/type")
    public ResponseEntity<?> getAllTransactionsByUserId(@PathVariable("userId") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자의 거래 정보가 없습니다.");
        }

        Map<LocalDate, List<TransactionDTO>> groupedTransactions = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCreatedAt().toLocalDate(),
                        Collectors.mapping(transaction -> {
                            TransactionDTO dto = new TransactionDTO();
                            dto.setAmount(transaction.getAmount());
                            dto.setCategory(transaction.getCategory().name());
                            dto.setDescription(transaction.getDescription());
                            dto.setType(transaction.getType().name());
                            return dto;
                        }, Collectors.toList())));

        List<Map<String, Object>> response = groupedTransactions.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> groupedData = new LinkedHashMap<>();
                    groupedData.put("createdAt", entry.getKey().toString());
                    groupedData.put("transactions", entry.getValue());
                    return groupedData;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getTransactionsByCategoryAndUserId(@PathVariable("userId") Long userId,
                                                                @PathVariable("category") String category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Category cat = Category.valueOf(category);
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndCategory(userId, cat);

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 카테고리에 대한 값이 없거나 사용자의 거래 정보가 없습니다.");
        }

        Map<LocalDate, List<TransactionDTO>> groupedTransactions = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getCreatedAt().toLocalDate(),
                        Collectors.mapping(transaction -> {
                            TransactionDTO dto = new TransactionDTO();
                            dto.setAmount(transaction.getAmount());
                            dto.setCategory(transaction.getCategory().name());
                            dto.setDescription(transaction.getDescription());
                            dto.setType(transaction.getType().name());
                            return dto;
                        }, Collectors.toList())));

        List<Map<String, Object>> response = groupedTransactions.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> groupedData = new LinkedHashMap<>();
                    groupedData.put("createdAt", entry.getKey().toString());
                    groupedData.put("transactions", entry.getValue());
                    return groupedData;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
