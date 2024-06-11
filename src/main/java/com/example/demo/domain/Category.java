package com.example.demo.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Category {
    식비,            // 식비
    교통수단,  // 교통수단
    문화생활,         // 문화생활
    금융,         // 금융
    기타,            // 기타
    저축;

    // 모든 카테고리 목록을 가져옴
    public static List<String> getAllCategories() {
        return Arrays.asList(values()).stream().map(Enum::name).collect(Collectors.toList());
    }
}
