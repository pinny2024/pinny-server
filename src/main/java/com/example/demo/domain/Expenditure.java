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
@Table(name = "expenditures")
public class Expenditure extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expenditureId")
    private Long expenditureId;

    @Column(name = "money")
    private int money;

    @Column(name = "category", length = 25)
    private String category;

    @Column(name = "content", length = 255)
    private String content;


    @Column(name = "image", length = 255)
    private String image;

}
