package com.example.demo.controller;

import com.example.demo.domain.Expenditure;
import com.example.demo.dto.ExpenditureDTO;
import com.example.demo.service.ExpenditureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenditures/{expenditureId}")
public class ExpenditureController {

    @Autowired
    private ExpenditureService expenditureService;

    @GetMapping
    public ResponseEntity<ExpenditureDTO> getExpenditureById(@PathVariable("expenditureId") Long expenditureId) {
        Expenditure expenditure = expenditureService.getExpenditureById(expenditureId);
        if (expenditure != null) {
            ExpenditureDTO expenditureDTO = convertToDTO(expenditure);
            return new ResponseEntity<>(expenditureDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private ExpenditureDTO convertToDTO(Expenditure expenditure) {
        ExpenditureDTO expenditureDTO = new ExpenditureDTO();
        expenditureDTO.setMoney(expenditure.getMoney());
        expenditureDTO.setCategory(expenditure.getCategory());
        expenditureDTO.setContent(expenditure.getContent());
        expenditureDTO.setCreatedAt(expenditure.getCreatedAt());
        return expenditureDTO;
    }

    @PostMapping
    public ResponseEntity<String> createExpenditure(@RequestBody Expenditure expenditure) {
        if (expenditure == null || expenditure.getMoney() == 0 || expenditure.getCategory() == null || expenditure.getContent() == null) {
            return new ResponseEntity<>("지출 정보를 입력해야 합니다.", HttpStatus.BAD_REQUEST);
        }

        Expenditure savedExpenditure = expenditureService.saveExpenditure(expenditure);
        if (savedExpenditure != null) {
            return new ResponseEntity<>("성공적으로 값이 들어갔습니다.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("성공적으로 값이 들어가지 못하였습니다. 결론은 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
