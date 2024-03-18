package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.domain.User;
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
        // Create a new user
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
        user.setBadge(userDTO.getBadge());

        // Save the new user
        return userRepository.save(user);
    }

    public String loginUser(UserDTO userDTO) {
        User user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            return "{\"userId\": " + user.getUserId() + ", \"message\": \"로그인이 완료되었습니다. 월 만 원씩 내시면 모든 기능을 자유롭게 이용하실 수 있습니다!\"}";
        } else {
            return "Invalid email or password!";
        }
    }
}
