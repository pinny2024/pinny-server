package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "plans")
public class Plan extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long planId;

    @CollectionTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "plan")
    private String plan;

    @Column(name = "image")
    private String image;

    @Column(name = "isChecked")
    private Boolean isChecked;

    @Column(name = "date")
    private LocalDateTime date;

    public void update(Long userId, String plan, String image, Boolean isChecked, LocalDateTime date) {
        this.userId = userId;
        this.plan = plan;
        this.image = image;
        this.isChecked = isChecked;
        this.date = date;
    }

}