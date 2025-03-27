package com.reverie_unique.reverique.domain.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q ORDER BY RAND() LIMIT 1")
    Question getRandomQuestion();
}