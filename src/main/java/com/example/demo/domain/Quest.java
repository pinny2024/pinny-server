package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "quests")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Quest extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questId", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "quest", nullable = false)
    private String quest;

    @Column(name = "price", nullable = false)
    private Integer price;

    @CreatedDate
    @Column(name = "startTime", nullable = false, updatable = false)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "endTime")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public void update(String quest, Integer price, LocalDateTime endTime) {
        this.quest = quest;
        this.price = price;
        this.endTime = endTime;
    }
}
