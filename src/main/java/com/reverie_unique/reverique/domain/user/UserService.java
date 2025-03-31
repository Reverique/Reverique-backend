package com.reverie_unique.reverique.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // coupleId에 해당하는 두 사용자를 조회하는 메서드
    public List<User> getUsersByCoupleId(Long coupleId) {
        return userRepository.findByCoupleId(coupleId);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}