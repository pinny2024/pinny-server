package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.QuestService;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions/{userId}")
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;
    private final QuestService questService;

    @Autowired
    public TransactionController(TransactionService transactionService, UserRepository userRepository, QuestService questService) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
        this.questService = questService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@PathVariable("userId") Long userId, @RequestBody Transaction transaction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        transaction.setUser(user);
        if (transaction.getCreatedAt() == null) {
            // 한국 표준시로 현재 시간 설정
            ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");
            ZonedDateTime koreaNow = ZonedDateTime.now(koreaZoneId);
            transaction.setCreatedAt(koreaNow.toLocalDateTime());
        }

        if (transaction.getCategory() == Category.저축) {
            List<Quest> quests = questService.findAllByUserId(userId);
            if (!quests.isEmpty()) {
                transaction.setQuest(quests.get(0));
            }
        } else {
            transaction.setQuest(null);
        }

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
                .sorted(Map.Entry.<LocalDate, List<TransactionDTO>>comparingByKey().reversed())
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
    public ResponseEntity<?> getTransactionsByCategory(@PathVariable("userId") Long userId, @PathVariable("category") Category category) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<Transaction> transactions = transactionService.getTransactionsByUserIdAndCategory(userId, category);

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 카테고리의 거래 정보가 없습니다.");
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
                .sorted(Map.Entry.<LocalDate, List<TransactionDTO>>comparingByKey().reversed())
                .map(entry -> {
                    Map<String, Object> groupedData = new LinkedHashMap<>();
                    groupedData.put("createdAt", entry.getKey().toString());
                    groupedData.put("transactions", entry.getValue());
                    return groupedData;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/저축/{questId}")
    public ResponseEntity<?> getSavingsTransactionsByQuestId(@PathVariable("userId") Long userId, @PathVariable("questId") Long questId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId).stream()
                .filter(transaction -> transaction.getCategory() == Category.저축 && transaction.getQuest() != null && transaction.getQuest().getId().equals(questId))
                .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 퀘스트의 저축 거래 정보가 없습니다.");
        }

        List<TransactionDTO> response = transactions.stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory().name());
                    dto.setDescription(transaction.getDescription());
                    dto.setType(transaction.getType().name());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<?> getTransactionsByDate(@PathVariable("userId") Long userId, @RequestParam("date") String dateStr) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr.trim()); // 개행 문자 및 공백 제거
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 날짜 형식입니다.");
        }

        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId).stream()
                .filter(transaction -> transaction.getCreatedAt().toLocalDate().equals(date))
                .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 날짜의 거래 정보가 없습니다.");
        }

        List<TransactionDTO> response = transactions.stream()
                .map(transaction -> {
                    TransactionDTO dto = new TransactionDTO();
                    dto.setAmount(transaction.getAmount());
                    dto.setCategory(transaction.getCategory().name());
                    dto.setDescription(transaction.getDescription());
                    dto.setType(transaction.getType().name());
                    return dto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/expenditures/{weeks}")
    public ResponseEntity<?> getWeeklyExpenditures(@PathVariable("userId") Long userId, @PathVariable("weeks") int weeks) {
        if (weeks < 1 || weeks > 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("기간은 1주일에서 4주일 사이여야 합니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        LocalDateTime startDate = user.getCreatedAt().toLocalDate().atStartOfDay();
        List<List<Map<String, Object>>> response = new ArrayList<>();

        for (int i = 0; i < weeks; i++) {
            LocalDateTime weekStartDate = startDate.plusWeeks(i);
            LocalDateTime weekEndDate = startDate.plusWeeks(i + 1);

            List<Transaction> transactions = transactionService.getTransactionsByUserIdAndType(userId, Type.지출).stream()
                    .filter(transaction -> !transaction.getCreatedAt().isBefore(weekStartDate) && transaction.getCreatedAt().isBefore(weekEndDate))
                    .collect(Collectors.toList());

            Map<Category, Double> expenditureSumByCategory = transactions.stream()
                    .filter(transaction -> transaction.getCategory() != Category.저축)
                    .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(transaction -> transaction.getAmount().doubleValue())));

            List<Map<String, Object>> weekData = expenditureSumByCategory.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .map(entry -> {
                        Map<String, Object> data = new HashMap<>();
                        data.put("category", entry.getKey().name());
                        data.put("expenditureSum", entry.getValue());
                        return data;
                    })
                    .collect(Collectors.toList());

            response.add(weekData);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}