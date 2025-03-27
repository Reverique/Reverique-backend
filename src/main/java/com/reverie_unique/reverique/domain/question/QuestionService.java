package com.reverie_unique.reverique.domain.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;

    }
    public List<Question> getRandomQuestion() {
        Question randomQuestion = questionRepository.getRandomQuestion();
        // 랜덤으로 선택된 질문을 반환
        return Collections.singletonList(randomQuestion);
    }
}