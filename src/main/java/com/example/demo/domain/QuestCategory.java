package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "quest_categories")
@Builder
public class QuestCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questCategoryId", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
