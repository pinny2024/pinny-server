package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Quest extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long questId;

    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quest")
    private String quest;

    @Column(name = "image")
    private String image;

    @Column(name = "startTime")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @Column(name = "endTime")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public void update(Long questId, Long userId, String quest, String image, LocalDateTime startTime, LocalDateTime endTime) {
        this.questId = questId;
        this.userId = userId;
        this.quest = quest;
        this.image = image;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
