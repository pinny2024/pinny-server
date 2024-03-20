package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String nickname;
    private String career;
    private String profile;
    private Integer salary;
    private Integer saving;
    private Integer ageRange;
    private String introduction;
    private List<String> badge;

}