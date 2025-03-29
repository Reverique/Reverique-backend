package com.reverie_unique.reverique.domain.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("answers")
public class AnswerController {

    @Autowired
    private  AnswerService answerService;

//    @GetMapping("test")
//    public String test(){
//        return answerService.findTodayAnswers(1L);
//    }
}
