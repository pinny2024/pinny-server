package com.example.demo.repository;

import com.example.demo.domain.Plan;
import com.example.demo.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByUserId(Long userId);

    @Query("SELECT q FROM Quest q WHERE q.user.id = :userId ORDER BY q.startTime DESC")
    List<Quest> findTopByUserIdOrderByStartTimeDesc(@Param("userId") Long userId);
}