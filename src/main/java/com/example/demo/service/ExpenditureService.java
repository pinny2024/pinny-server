package com.example.demo.service;

import com.example.demo.domain.Expenditure;
import com.example.demo.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenditureService {

    @Autowired
    private ExpenditureRepository expenditureRepository;

    public Expenditure getExpenditureById(Long expenditureId) {
        return expenditureRepository.findById(expenditureId).orElse(null);
    }

    public Expenditure saveExpenditure(Expenditure expenditure) {
        return expenditureRepository.save(expenditure);
    }

    public List<Expenditure> getAllExpenditures() {
        return expenditureRepository.findAll();
    }

    public List<Expenditure> getExpendituresByCategory(String category) {
        return expenditureRepository.findByCategory(category);
    }

    public List<Expenditure> getExpendituresByUserId(Long userId) {
        return expenditureRepository.findByUserId(userId);
    }

    public List<Expenditure> getExpendituresByUserIdAndCategory(Long userId, String category) {
        return expenditureRepository.findByUserIdAndCategory(userId, category);
    }
}
