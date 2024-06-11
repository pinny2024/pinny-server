package com.example.demo.dto.category;

import com.example.demo.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private Long categoryId;
    private String name;

    public CategoryResponse(Category category) {
        this.categoryId = category.getId();
        this.name = category.getName();
    }
}
