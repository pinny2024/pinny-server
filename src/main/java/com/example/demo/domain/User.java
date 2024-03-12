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
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long userId;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "career")
    private String career;

    @Column(name = "profile")
    private String profile;

    @Column(name = "salary")
    private int salary;

    @Column(name = "saving")
    private int saving;

    @Column(name = "ageRange")
    private int ageRange;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "badge")
    private String[] badge;

   public void update(long userId, String email, String password, String nickname, String career,String profile,int salary,int saving, int ageRange,String introduction,String[] badge){
       this.userId=userId;
       this.email=email;
       this.password=password;
       this.nickname=nickname;
       this.career=career;
       this.profile=profile;
       this.salary=salary;
       this.saving=saving;
       this.ageRange=ageRange;
       this.introduction=introduction;
       this.badge=badge;
   }
}
