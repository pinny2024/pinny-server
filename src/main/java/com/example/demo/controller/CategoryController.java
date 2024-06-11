package com.example.demo.controller;

import com.example.demo.domain.Category;
import com.example.demo.dto.category.AddCategoryRequest;
import com.example.demo.dto.category.CategoryResponse;
import com.example.demo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody AddCategoryRequest request) {
        Category savedCategory = categoryService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CategoryResponse(savedCategory));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> findCategory(@PathVariable("categoryId") Long id) {
        Category category = categoryService.findById(id);

        return ResponseEntity.ok()
                .body(new CategoryResponse(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll()
                .stream()
                .map(CategoryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(categories);
    }
}
