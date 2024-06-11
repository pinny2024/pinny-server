package com.example.demo.dto;

import java.time.LocalDateTime;

public class IncomeDTO {
    private int money;
    private int categoryId; // categoryId 필드 추가
    private String content;
    private LocalDateTime createdAt;
    private Long userId; // userId 필드 추가
    private String category;
    public int getMoney() {
        return money;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
