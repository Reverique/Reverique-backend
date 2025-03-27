//package com.reverie_unique.reverique.domain.answer;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public interface AnswerRepository extends JpaRepository<Answer, Long> {
//    List<Answer> findByUserIdAndCoupleIdAndCreatedAt(LocalDate createdAt, Long userId, Long coupleId);  // 오늘 받은 질문 확인
//    List<Answer> findByUserIdAndCoupleId(Long userId, Long coupleId);  // 유저와 커플이 받은 모든 질문 확인
//}