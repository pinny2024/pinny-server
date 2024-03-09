package com.example.demo.repository;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

}