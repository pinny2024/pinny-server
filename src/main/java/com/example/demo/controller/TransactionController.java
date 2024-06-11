package com.example.demo.controller;

import com.example.demo.domain.Category;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.User;
import com.example.demo.dto.CategoryNotFoundException;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.repository.UserRepository;
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
@RequestMapping("/transactions/{userId}")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository; // Autowiring UserRepository

    @Autowired
    public TransactionController(TransactionService transactionService, UserRepository userRepository) {
        this.transactionService = transactionService;
        this.userRepository = userRepository; // Initializing UserRepository
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("userId") Long userId, @RequestBody Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        transaction.setUser(user);
        transaction.setCreatedAt(LocalDateTime.now());

        Transaction savedTransaction = transactionService.saveTransaction(transaction);

        // 사용자 정보를 제외한 거래 정보만 반환합니다.
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(savedTransaction.getAmount());
        dto.setCategory(savedTransaction.getCategory().name());
        dto.setDescription(savedTransaction.getDescription());
        dto.setType(savedTransaction.getType().name());
        dto.setCreatedAt(savedTransaction.getCreatedAt());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }



    @GetMapping("/type")
    public ResponseEntity<?> getAllTransactionsByUserId(@PathVariable("userId") Long userId) {
        // 사용자 ID가 유효한지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 사용자 ID가 유효하면 해당 사용자의 거래를 반환
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);

        // 거래가 없는 경우에 대한 처리
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자의 거래 정보가 없습니다.");
        }

        // 거래 정보를 DTO로 변환하여 반환
        List<TransactionDTO> dtos = transactions.stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory().name());
                    dto.setDescription(transaction.getDescription());
                    dto.setType(transaction.getType().name());
                    dto.setCreatedAt(transaction.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getTransactionsByCategoryAndUserId(@PathVariable("userId") Long userId,
                                                                @PathVariable("category") String category) {
        // 사용자 ID가 유효한지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        // 사용자 ID가 유효하면 해당 사용자의 거래를 카테고리별로 필터링하여 반환
        Category cat = Category.valueOf(category);
        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndCategory(userId, cat);

        // 거래가 없는 경우에 대한 처리
        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 카테고리에 대한 값이 없거나 사용자의 거래 정보가 없습니다.");
        }

        // 거래 정보를 DTO로 변환하여 반환
        List<TransactionDTO> dtos = transactions.stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory().name());
                    dto.setDescription(transaction.getDescription());
                    dto.setType(transaction.getType().name());
                    dto.setCreatedAt(transaction.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


}
