package com.reverie_unique.reverique.domain.user.repository;

import com.reverie_unique.reverique.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // coupleId에 해당하는 두 사용자를 찾는 메서드
    List<User> findByCoupleId(Long coupleId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}