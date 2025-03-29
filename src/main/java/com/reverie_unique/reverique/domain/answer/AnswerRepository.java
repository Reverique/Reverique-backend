package com.reverie_unique.reverique.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.coupleId = :coupleId " +
        "AND YEAR(a.createdAt) = YEAR(CURDATE()) " +
        "AND MONTH(a.createdAt) = MONTH(CURDATE()) " +
        "AND DAY(a.createdAt) = DAY(CURDATE()) " +
        "AND a.deleted = 0")
    List<Answer> findTodayAnswers(@Param("coupleId") Long coupleId);
}