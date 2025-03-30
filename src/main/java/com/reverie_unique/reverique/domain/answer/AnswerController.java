package com.reverie_unique.reverique.domain.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.SystemEnvironmentPropertySourceEnvironmentPostProcessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("answers")
public class AnswerController {

    @Autowired
    private  AnswerService answerService;

    @PatchMapping("/{id}")
    public String updateAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        System.out.println(answer);
        boolean isUpdated = answerService.updateAnswer(id, answer);
        if (isUpdated) {
            return "Answer updated successfully!";
        } else {
            return "Answer not found!";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long id) {
        boolean isDeleted = answerService.deleteAnswer(id);
        if (isDeleted) {
            return "Answer deleted successfully!";
        } else {
            return "Answer not found!";
        }
    }
}
