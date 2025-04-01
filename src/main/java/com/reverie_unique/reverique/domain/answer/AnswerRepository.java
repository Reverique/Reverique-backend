package com.reverie_unique.reverique.domain.answer;

import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT a FROM Answer a WHERE a.coupleId = :coupleId " +
            "AND EXTRACT(YEAR FROM a.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM a.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(DAY FROM a.createdAt) = EXTRACT(DAY FROM CURRENT_DATE) " +
            "AND a.deleted = 0")
    List<Answer> findTodayAnswers(@Param("coupleId") Long coupleId);


    @Query(value = "SELECT NEW com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse(" +
            "    a.questionId, " +
            "    q.content, " +
            "    MAX(CASE WHEN a.userId = :userId1 THEN a.answer END), " +
            "    MAX(CASE WHEN a.userId = :userId2 THEN a.answer END), " +
            "    MAX(a.createdAt)) " +
            "FROM Answer a " +
            "JOIN Question q ON a.questionId = q.id " +
            "WHERE " +
            "    a.coupleId = :coupleId " +
            "    AND a.deleted = 0 " +
            "GROUP BY " +
            "    a.questionId, q.content " +
            "ORDER BY " +
            "    MAX(a.createdAt) DESC")
    Page<QuestionAnswerResponse> getAnswers(@Param("userId1") Long userId1,
                                                  @Param("userId2") Long userId2,
                                                  @Param("coupleId") Long coupleId,
                                                  Pageable pageable);
}
