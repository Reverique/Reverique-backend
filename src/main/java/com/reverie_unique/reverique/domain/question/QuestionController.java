package com.reverie_unique.reverique.domain.question;


import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/questions")

public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/random")
    public QuestionAnswerResponse getRandomQuestion() {
        // questionService.getRandomQuestion(1L, 1L)로 수정
        return questionService.getRandomQuestion(1L, 1L);
    }

//    @GetMapping("/test")
//    public String test(){
//        return questionService.test();
//    }

}