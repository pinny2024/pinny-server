package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDTO userDTO) {
        User user = new User();
        user.setSaving(userDTO.getSaving());
        user.setEmail(userDTO.getEmail());
        user.setCareer(userDTO.getCareer());
        user.setBadges(userDTO.getBadge());
        user.setSalary(userDTO.getSalary());
        user.setAgeRange(userDTO.getAgeRange());
        user.setPassword(userDTO.getPassword());
        user.setNickname(userDTO.getNickname());
        user.setProfile(userDTO.getProfile());
        user.setProfile(userDTO.getProfile());
        user.setIntroduction(userDTO.getIntroduction());
                return userRepository.save(user);
    }
}