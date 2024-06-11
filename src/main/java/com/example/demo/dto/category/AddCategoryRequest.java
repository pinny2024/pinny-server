package com.example.demo.dto.category;

import com.example.demo.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddCategoryRequest {
    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(name)
                .build();
    }
}
