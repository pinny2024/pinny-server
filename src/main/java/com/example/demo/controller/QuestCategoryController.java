package com.example.demo.controller;

import com.example.demo.domain.Category;
import com.example.demo.domain.QuestCategory;
import com.example.demo.dto.category.AddCategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.dto.questCategory.AddQuestCategoryRequest;
import com.example.demo.dto.questCategory.QuestCategoryResponse;
import com.example.demo.repository.QuestCategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.QuestCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/quest-categories")
public class QuestCategoryController {

    private final QuestCategoryService categoryService;

    @PostMapping
    public ResponseEntity<QuestCategoryResponse> addCategory(@RequestBody AddQuestCategoryRequest request) {
        QuestCategory savedCategory = categoryService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new QuestCategoryResponse(savedCategory));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<QuestCategoryResponse> findCategory(@PathVariable("categoryId") Long id) {
        QuestCategory category = categoryService.findById(id);

        return ResponseEntity.ok()
                .body(new QuestCategoryResponse(category));
    }

    @GetMapping
    public ResponseEntity<List<QuestCategoryResponse>> findAllCategories() {
        List<QuestCategoryResponse> categories = categoryService.findAll()
                .stream()
                .map(QuestCategoryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(categories);
    }
}
