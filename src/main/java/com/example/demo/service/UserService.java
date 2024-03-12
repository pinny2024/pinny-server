package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.domain.User; // User 클래스 import 수정
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        // Convert DTO to entity
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setNickname(userDTO.getNickname());
        user.setCareer(userDTO.getCareer());
        user.setProfile(userDTO.getProfile());
        user.setSalary(userDTO.getSalary());
        user.setSaving(userDTO.getSaving());
        user.setAgeRange(userDTO.getAgeRange());
        user.setIntroduction(userDTO.getIntroduction());
        user.setBadge(userDTO.getBadge()); // setBadge()로 변경

        // Save entity
        return userRepository.save(user);
    }
}
