package com.reverie_unique.reverique.domain.user.service;

import com.reverie_unique.reverique.common.jwt.JwtTokenProvider;
import com.reverie_unique.reverique.domain.auth.Service.EmailService;
import com.reverie_unique.reverique.domain.couple.Couple;
import com.reverie_unique.reverique.domain.couple.CoupleRepository;
import com.reverie_unique.reverique.domain.user.dto.*;
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
    private final EmailService emailService;
    private final JwtTokenProvider jwtProvider;
    private final CoupleRepository coupleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService, JwtTokenProvider jwtProvider, CoupleRepository coupleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.jwtProvider = jwtProvider;
        this.coupleRepository = coupleRepository;
    }

    // coupleId에 해당하는 두 사용자를 조회하는 메서드
    public List<User> getUsersByCoupleId(Long coupleId) {
        return userRepository.findByCoupleId(coupleId);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void signup(UserSignupDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (!emailService.isEmailVerified(request.getEmail())) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
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
            user.setProfile(request.getProfile());

        userRepository.save(user);
    }

    public UserInfoDTO getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        PartnerInfoDTO partnerInfo = getPartnerInfo(user.getCoupleId(), userId);

        return new UserInfoDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getNickName(),
                user.getBirthDate(),
                user.getGender(),
                user.getProfile(),
                partnerInfo
        );
    }

    // 유저 정보 업데이트
    public UserUpdateResDTO updateUserInfo(Long userId, UserUpdateReqDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.setNickName(dto.getNickname());
        user.setBirthDate(dto.getBirthDate());
        user.setGender(dto.getGender());

        userRepository.save(user);

        String newAccessToken = jwtProvider.generateAccessToken(user);

        UserInfoDTO updatedUser = getUserInfo(userId);

        return new UserUpdateResDTO(updatedUser, newAccessToken);
    }

    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        userRepository.delete(user);
    }

    public void changePassword(Long userId, PasswordChangeReqDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    public PartnerInfoDTO getPartnerInfo(Long coupleId, Long userId) {

        if (coupleId == null) return null;

        Couple couple = coupleRepository.findById(coupleId).orElse(null);
        if (couple == null) return null;

        Long partnerId;
        if (userId.equals(couple.getUser1Id())) {
            partnerId = couple.getUser2Id();
        } else if (userId.equals(couple.getUser2Id())) {
            partnerId = couple.getUser1Id();
        } else {
            return null;
        }

        User partner = userRepository.findById(partnerId).orElse(null);
        if (partner == null) return null;

        return new PartnerInfoDTO(
                partner.getName(),
                partner.getNickName(),
                partner.getBirthDate(),
                partner.getGender(),
                couple.getStartDate().toString()
        );
    }
}