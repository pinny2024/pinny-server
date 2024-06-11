package com.example.demo.domain;

import com.example.demo.domain.BaseTimeEntity;
import com.example.demo.domain.User;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "planId", updatable = false, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "plan", nullable = false)
    private String plan;

    @Column(name = "image")
    private String image;

    @Column(name = "isChecked")
    @Builder.Default
    private boolean isChecked = false;

    public void update(String plan, String image, Boolean isChecked) {
        this.plan = plan;
        this.image = image;
        this.isChecked = isChecked != null ? isChecked : false;
    }

    public Boolean getIsChecked() {
        return false;
    }
}
