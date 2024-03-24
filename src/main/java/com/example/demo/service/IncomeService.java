package com.example.demo.service;

import com.example.demo.domain.Income;
import com.example.demo.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public Income getIncomeById(Long incomeId) {
        return incomeRepository.findById(incomeId).orElse(null);
    }

    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }
}
