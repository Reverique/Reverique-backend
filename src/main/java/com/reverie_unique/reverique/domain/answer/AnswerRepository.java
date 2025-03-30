package com.reverie_unique.reverique.domain.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.coupleId = :coupleId " +
            "AND EXTRACT(YEAR FROM a.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM a.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(DAY FROM a.createdAt) = EXTRACT(DAY FROM CURRENT_DATE) " +
            "AND a.deleted = 0")
    List<Answer> findTodayAnswers(@Param("coupleId") Long coupleId);
}