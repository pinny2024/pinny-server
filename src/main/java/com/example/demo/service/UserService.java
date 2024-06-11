package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(UserDTO userDTO) {
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
        return userRepository.save(user);
    }


    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public User updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setProfile(userDTO.getProfile());
            return userRepository.save(user);
        }
        return null;
    }
}
