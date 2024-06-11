package com.example.demo.repository;

import com.example.demo.domain.QuestCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestCategoryRepository extends JpaRepository<QuestCategory, Long> {

}
