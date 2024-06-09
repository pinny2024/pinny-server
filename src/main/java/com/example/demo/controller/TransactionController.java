package com.example.demo.controller;

import com.example.demo.domain.Expenditure;
import com.example.demo.domain.Income;
import com.example.demo.dto.TransactionDTO;
import com.example.demo.service.ExpenditureService;
import com.example.demo.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private ExpenditureService expenditureService;

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactionDTOs = new ArrayList<>();

        // 모든 지출 데이터
        List<Expenditure> expenditures = expenditureService.getAllExpenditures();
        for (Expenditure expenditure : expenditures) {
            transactionDTOs.add(convertToDTO(expenditure));
        }

        // 그 다음 모든 수입 데이터
        List<Income> incomes = incomeService.getAllIncomes();
        for (Income income : incomes) {
            transactionDTOs.add(convertToDTO(income));
        }

        // 입력된 순서대로 반환
        transactionDTOs.sort((t1, t2) -> t1.getCreatedAt().compareTo(t2.getCreatedAt()));

        return new ResponseEntity<>(transactionDTOs, HttpStatus.OK);
    }

    private TransactionDTO convertToDTO(Expenditure expenditure) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(expenditure.getExpenditureId());
        transactionDTO.setMoney(expenditure.getMoney());
        transactionDTO.setCategory(expenditure.getCategory());
        transactionDTO.setContent(expenditure.getContent());
        transactionDTO.setCreatedAt(expenditure.getCreatedAt());
        transactionDTO.setType("지출");
        return transactionDTO;
    }

    private TransactionDTO convertToDTO(Income income) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(income.getIncomeId());
        transactionDTO.setMoney(income.getMoney());
        transactionDTO.setCategory(income.getCategory());
        transactionDTO.setContent(income.getContent());
        transactionDTO.setCreatedAt(income.getCreatedAt());
        transactionDTO.setType("수입");
        return transactionDTO;
    }
}
