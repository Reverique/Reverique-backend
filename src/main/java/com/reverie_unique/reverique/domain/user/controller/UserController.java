package com.reverie_unique.reverique.domain.user.controller;

import com.reverie_unique.reverique.domain.user.dto.UserSignupDTO;
import com.reverie_unique.reverique.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupDTO request) {
        userService.signup(request);
        return ResponseEntity.ok("회원가입 성공");
    }
}