package com.example.demo.domain;

import jakarta.persistence.*;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "password", length = 20)
    private String password;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "career", length = 20)
    private String career;

    @Column(name = "profile", length = 255)
    private String profile;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "saving")
    private Integer saving;

    @Column(name = "ageRange")
    private Integer ageRange;

    @Column(name = "introduction", length = 255)
    private String introduction;

    @ElementCollection
    @CollectionTable(name = "user_badges", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "badge")
    private List<String> badges;

}