package com.reverie_unique.reverique.domain.question;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")

public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/random")
    public List<Question> getRandomQuestion() {
        List<Question> questions = questionService.getRandomQuestion();
        System.out.println(questions);
        return questions;
    }
}