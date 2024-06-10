package com.example.demo.controller;

import com.example.demo.domain.Expenditure;
import com.example.demo.domain.User;
import com.example.demo.dto.ExpenditureDTO;
import com.example.demo.service.ExpenditureService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenditures")
public class ExpenditureController {

    @Autowired
    private ExpenditureService expenditureService;

    @Autowired
    private UserService userService;

    @GetMapping("/{expenditureId}")
    public ResponseEntity<ExpenditureDTO> getExpenditureById(@PathVariable("expenditureId") Long expenditureId) {
        Expenditure expenditure = expenditureService.getExpenditureById(expenditureId);
        if (expenditure != null) {
            ExpenditureDTO expenditureDTO = convertToDTO(expenditure);
            return new ResponseEntity<>(expenditureDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createExpenditure(@PathVariable("userId") Long userId, @RequestBody ExpenditureDTO expenditureDTO) {
        if (expenditureDTO == null || expenditureDTO.getMoney() == 0 || expenditureDTO.getCategory() == null || expenditureDTO.getContent() == null) {
            return new ResponseEntity<>("지출 정보를 입력해야 합니다.", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>("유효하지 않은 사용자 ID입니다.", HttpStatus.BAD_REQUEST);
        }

        Expenditure expenditure = convertToEntity(expenditureDTO, user);
        Expenditure savedExpenditure = expenditureService.saveExpenditure(expenditure);
        if (savedExpenditure != null) {
            return new ResponseEntity<>("성공적으로 값이 들어갔습니다.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("성공적으로 값이 들어가지 못하였습니다. 결론은 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
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

    private Expenditure convertToEntity(ExpenditureDTO expenditureDTO, User user) {
        return Expenditure.builder()
                .money(expenditureDTO.getMoney())
                .category(expenditureDTO.getCategory())
                .content(expenditureDTO.getContent())
                .user(user)
                .build();
    }
}
