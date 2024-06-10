package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> joinUser(@RequestBody UserDTO userDTO) {
        // 이미 가입된 이메일인지 확인
        if (userService.existsByEmail(userDTO.getEmail())) {
            // 이미 가입된 이메일이라면 에러 메시지 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "이미 가입된 이메일 주소입니다.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // 새로운 사용자 정보 저장
        User savedUser = userService.saveUser(userDTO);
        if (savedUser != null) {
            // 회원가입 성공 메시지와 함께 사용자 ID 반환
            Map<String, Object> response = new HashMap<>();
            response.put("userId", savedUser.getId());
            response.put("message", "회원가입이 완료되었습니다. 월 5천원씩 내시면 모든 기능을 자유롭게 이용하실 수 있습니다!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            // 회원가입 실패 시 에러 메시지 반환
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

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        User updatedUser = userService.updateUser(userId, userDTO);
        if (updatedUser != null) {
            // 사용자 ID가 포함된 응답 메시지 반환
            Map<String, Object> response = new HashMap<>();
            response.put("message", userId + " 계정이 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}