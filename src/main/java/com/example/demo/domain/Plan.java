package com.example.demo.domain;

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
@Table(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long planId;

    @Column
    @JoinColumn(name="userId", referencedColumnName = "userId")
    private long userId;

    @Column(name = "plan")
    private String plan;

    @Column(name = "image")
    private String image;

    @Column(name = "isChecked")
    private Boolean isChecked;

    @Column(name = "date")
    private LocalDateTime date;

    public void update(long planId, long userId, String plan, String image, Boolean isChecked, LocalDateTime date) {
        this.planId = planId;
        this.userId = userId;
        this.plan = plan;
        this.image = image;
        this.isChecked = isChecked;
        this.date = date;
    }

}