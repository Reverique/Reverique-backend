package com.reverie_unique.reverique.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.id NOT IN " +
            "(SELECT a.questionId FROM Answer a WHERE a.coupleId = :coupleId AND a.deleted = 0) " +
            "ORDER BY RAND() LIMIT 1")
    Optional<Question> getRandomQuestionExcludingAnswered(@Param("coupleId") Long coupleId);


    Optional<Question> findById(Long questionId);
}