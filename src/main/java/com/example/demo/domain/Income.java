package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "incomeId")
    private Long incomeId;

    @Column(name = "money")
    private int money;

    @Column(name = "category", length = 25)
    private String category;

    @Column(name = "content", length = 255)
    private String content;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "image", length = 255)
    private String image;

}
