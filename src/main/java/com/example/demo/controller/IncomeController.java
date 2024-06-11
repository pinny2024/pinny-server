package com.example.demo.controller;

import com.example.demo.domain.Income;
import com.example.demo.domain.User;
import com.example.demo.dto.IncomeDTO;
import com.example.demo.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping("/{incomeId}")
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable("incomeId") Long incomeId) {
        Income income = incomeService.getIncomeById(incomeId);
        if (income != null) {
            IncomeDTO incomeDTO = convertToDTO(income);
            return new ResponseEntity<>(incomeDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<IncomeDTO>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        List<IncomeDTO> incomeDTOs = incomes.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(incomeDTOs, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<IncomeDTO>> getIncomesByCategory(@PathVariable("category") String category) {
        List<Income> incomes = incomeService.getIncomesByCategory(category);
        List<IncomeDTO> incomeDTOs = incomes.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new ResponseEntity<>(incomeDTOs, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> createIncome(@PathVariable("userId") Long userId, @RequestBody IncomeDTO incomeDTO) {
        try {
            // 유효하지 않은 사용자일 경우 예외처리
            if (!isValidUser(userId)) {
                return new ResponseEntity<>("유효하지 않은 사용자입니다.", HttpStatus.BAD_REQUEST);
            }

            // 수입 정보가 유효한지 검사
            if (incomeDTO == null || incomeDTO.getMoney() == 0 || incomeDTO.getCategoryId() == 0 || incomeDTO.getContent() == null) {
                return new ResponseEntity<>("수입 정보를 입력해야 합니다.", HttpStatus.BAD_REQUEST);
            }

            // 받은 userId로 IncomeDTO 객체에 userId 설정
            incomeDTO.setUserId(userId);

            Income income = convertToEntity(incomeDTO); // DTO를 엔티티로 변환
            Income savedIncome = incomeService.saveIncome(income);
            if (savedIncome != null) {
                return new ResponseEntity<>("성공적으로 값이 들어갔습니다.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("성공적으로 값이 들어가지 못하였습니다. 결론은 실패!", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("요청을 처리하는 중에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 사용자가 유효한지 검사하는 메서드
    private boolean isValidUser(Long userId) {
        // 여기에 사용자 유효성을 검사하는 코드를 작성합니다. 예를 들어, 사용자가 데이터베이스에 존재하는지 확인할 수 있습니다.
        // 예제로 간단하게 사용자 ID가 양수인지 확인합니다.
        return userId != null && userId > 0;
    }


    private Income convertToEntity(IncomeDTO incomeDTO) {
        return Income.builder()
                .money(incomeDTO.getMoney())
                .category(String.valueOf(incomeDTO.getCategoryId())) // categoryId를 문자열로 변환
                .content(incomeDTO.getContent())
                .build();
    }


    @DeleteMapping("/{incomeId}")
    public ResponseEntity<String> deleteIncome(@PathVariable("incomeId") Long incomeId) {
        incomeService.deleteIncome(incomeId);
        return new ResponseEntity<>("성공적으로 삭제되었습니다.", HttpStatus.OK);
    }

    @PutMapping("/{incomeId}")
    public ResponseEntity<String> updateIncome(@PathVariable("incomeId") Long incomeId, @RequestBody Income income) {
        Income updatedIncome = incomeService.updateIncome(incomeId, income);
        if (updatedIncome != null) {
            return new ResponseEntity<>("성공적으로 수정되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("수정에 실패했습니다.", HttpStatus.NOT_FOUND);
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
}
