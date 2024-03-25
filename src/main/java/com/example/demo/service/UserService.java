package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDTO userDTO) {
        // 이미 가입된 이메일인지 확인
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return null; // 이미 가입된 이메일이라면 null 반환
        }

        // 새로운 사용자 정보 저장
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
        user.setIntroduction(userDTO.getIntroduction());
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // 로그인 실패
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public User updateUser(Long userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setNickname(userDTO.getNickname());
            user.setCareer(userDTO.getCareer());
            user.setProfile(userDTO.getProfile());
            user.setSalary(userDTO.getSalary());
            user.setSaving(userDTO.getSaving());
            user.setAgeRange(userDTO.getAgeRange());
            user.setIntroduction(userDTO.getIntroduction());
            //user.setBadge(userDTO.getBadge());
            return userRepository.save(user);
        }
        return null;
    }

    public boolean existsByEmail(String email) {
        return true;
    }
}
