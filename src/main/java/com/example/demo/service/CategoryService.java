package com.example.demo.service;

import com.example.demo.domain.Category;
import com.example.demo.dto.category.AddCategoryRequest;
import com.example.demo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category save(AddCategoryRequest request) {
        return categoryRepository.save(request.toEntity());
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+id));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
