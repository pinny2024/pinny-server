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

    @Column(name = "checkNum")
    @Builder.Default
    private Integer checkNum = 0;

    @Column(name = "isChecked")
    @Builder.Default
    private Boolean isChecked = false;

    @Column(name = "isClosed")
    @Builder.Default
    private Boolean isClosed = false;

    @Column(name = "lastCheckedAt")
    private LocalDateTime lastCheckedAt;

    public void update(String plan, String image, Integer checkNum, Boolean isChecked, Boolean isClosed) {
        this.plan = plan;
        this.image = image;
        this.checkNum = checkNum;
        this.isChecked = isChecked != null ? isChecked : false;
        this.isClosed = isClosed != null ? isClosed : false;
    }

    public void check() {
        if (isClosed) {
            throw new IllegalStateException("Plan is closed and cannot be checked.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (isChecked) {
            if (checkNum > 0) {
                checkNum--;
            }
            isChecked = false;
            lastCheckedAt = now.minusDays(1);
        } else {
            if (checkNum >= 7) {
                throw new IllegalStateException("Check number cannot exceed 7.");
            }
            if (lastCheckedAt != null && lastCheckedAt.toLocalDate().isEqual(now.toLocalDate())) {
                throw new IllegalStateException("Can only check once per day.");
            }
            checkNum++;
            isChecked = true;
            lastCheckedAt = now;
        }
    }

    public Boolean getIsChecked() {
        return this.isChecked;
    }

    public Boolean getIsClosed() {
        return this.isClosed;
    }

    public Integer getCheckNum() {
        return this.checkNum;
    }
}
