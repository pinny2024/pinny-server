package com.example.demo.dto;

import org.hibernate.mapping.List;

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
    private String[] badge;


    public String getEmail() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getNickname() {
        return null;
    }

    public String getCareer() {
        return null;
    }

    public String getProfile() {
        return null;
    }

    public int getSalary() {
        return 0;
    }

    public int getSaving() {
        return 0;
    }

    public int getAgeRange() {
        return 0;
    }

    public String getIntroduction() {
        return null;
    }

    public String[] getBadge() {
        return new String[0];
    }
}
