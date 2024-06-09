package com.example.demo.controller;

import com.example.demo.domain.Income;
import com.example.demo.dto.IncomeDTO;
import com.example.demo.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incomes/{incomeId}")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable("incomeId") Long incomeId) {
        Income income = incomeService.getIncomeById(incomeId);
        if (income != null) {
            IncomeDTO incomeDTO = convertToDTO(income);
            return new ResponseEntity<>(incomeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private IncomeDTO convertToDTO(Income income) {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setMoney(income.getMoney());
        incomeDTO.setCategory(income.getCategory());
        incomeDTO.setContent(income.getContent());
        incomeDTO.setCreatedAt(income.getCreatedAt());
        return incomeDTO;
    }

    @PostMapping
    public ResponseEntity<String> createIncome(@RequestBody Income income) {
        if (income == null || income.getMoney() == 0 || income.getCategory() == null || income.getContent() == null) {
            return new ResponseEntity<>("수입 정보를 입력해야 합니다.", HttpStatus.BAD_REQUEST);
        }

        Income savedIncome = incomeService.saveIncome(income);
        if (savedIncome != null) {
            return new ResponseEntity<>("성공적으로 값이 들어갔습니다.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("성공적으로 값이 들어가지 못하였습니다. 결론은 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
