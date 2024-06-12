package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinUser(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("nickname") String nickname,
            @RequestParam("career") String career,
            @RequestParam("salary") Integer salary,
            @RequestParam("saving") Integer saving,
            @RequestParam("ageRange") Integer ageRange,
            @RequestParam("introduction") String introduction) {

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setNickname(nickname);
        userDTO.setCareer(career);
        userDTO.setSalary(salary);
        userDTO.setSaving(saving);
        userDTO.setAgeRange(ageRange);
        userDTO.setIntroduction(introduction);

        if (userService.existsByEmail(userDTO.getEmail())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "이미 가입된 이메일 주소입니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String filePath = uploadDir + "/" + fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace(); // 에러 처리 필요
        }

        userDTO.setProfile(filePath);
        User savedUser = userService.saveUser(userDTO);
        if (savedUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", savedUser.getId());
            response.put("message", "회원가입이 완료되었습니다. 월 5천원씩 내시면 모든 기능을 자유롭게 이용하실 수 있습니다!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "회원가입에 실패했습니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            // 로그인 성공 메시지와 함께 사용자 ID 반환
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getId());
            response.put("message", "로그인이 완료되었습니다. 월 만 원씩 내시면 모든 기능을 자유롭게 이용하실 수 있습니다!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 로그인 실패 시 에러 메시지 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "이메일 또는 비밀번호가 올바르지 않습니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/users")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);
        if (deleted) {
            return new ResponseEntity<>("탈퇴가 완료되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/nickname/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable Long userId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "career", required = false) String career) {

        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 프로필 이미지 업로드 처리
//        if (file != null) {
//            // 프로필 이미지 업로드 처리 코드
//        }

        // 닉네임과 직업 업데이트
        if (nickname != null && !nickname.equals(user.getNickname())) {
            user.setNickname(nickname);
        } else if (nickname != null) {
            // 동일한 닉네임이 이미 존재하는 경우
            Map<String, Object> response = new HashMap<>();
            response.put("message", "동일한 닉네임이 이미 존재합니다. 다른 닉네임을 입력하세요.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if (career != null && !career.equals(user.getCareer())) {
            user.setCareer(career);
        } else if (career != null) {
            // 동일한 직업이 이미 존재하는 경우
            Map<String, Object> response = new HashMap<>();
            response.put("message", "동일한 직업이 이미 존재합니다. 다른 직업을 입력하세요.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User updatedUser = userService.updateUser(user);
        if (updatedUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "계정이 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
