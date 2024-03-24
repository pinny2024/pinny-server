package com.example.demo.controller;

import com.example.demo.domain.Expenditure;
import com.example.demo.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/expenditures/{userId}")
public class ExpenditureController {

    @Autowired
    private ExpenditureService expenditureService;

    @PostMapping
    public ResponseEntity<String> createExpenditure(@RequestBody Expenditure expenditure) {

        if (expenditure.getCreatedAt() == null) {
            expenditure.setCreatedAt(LocalDateTime.now());
        }

        Expenditure savedExpenditure = expenditureService.saveExpenditure(expenditure);
        if (savedExpenditure != null) {
            return new ResponseEntity<>("성공적으로 값이 들어갔습니다.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("성공적으로 값이 들어가지 못하였습니다. 결론은 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
