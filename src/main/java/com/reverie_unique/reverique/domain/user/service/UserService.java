package com.reverie_unique.reverique.domain.user.service;

import com.reverie_unique.reverique.domain.user.dto.UserSignupDTO;
import com.reverie_unique.reverique.domain.user.entity.User;
import com.reverie_unique.reverique.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // coupleId에 해당하는 두 사용자를 조회하는 메서드
    public List<User> getUsersByCoupleId(Long coupleId) {
        return userRepository.findByCoupleId(coupleId);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User signup(UserSignupDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setName(request.getName());
            user.setNickName(request.getNickname());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setBirthDate(request.getBirthDate());
            user.setGender(request.getGender());
            user.setCoupleId(null); // 커플 매칭 전
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.setAddress(request.getAddress());

        return userRepository.save(user);
    }

}