package com.reverie_unique.reverique.domain.question;


import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.constant.ApiStatus;
import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/questions")

public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/daily-question")
    public ApiResponse<QuestionAnswerResponse> getRandomQuestion(@RequestParam Long userId, @RequestParam Long coupleId) {
        try {
            QuestionAnswerResponse questionAnswerResponse = questionService.getRandomQuestion(userId, coupleId);
            if (questionAnswerResponse == null) {
                return new ApiResponse<>(ApiStatus.FAILURE, ApiStatus.STATUS_NOT_FOUND, ApiStatus.NOT_FOUND_MESSAGE, null);
            }
            return new ApiResponse<>(ApiStatus.SUCCESS, ApiStatus.STATUS_OK, ApiStatus.MESSAGE_SUCCESS, questionAnswerResponse);
        } catch (Exception e) {
            throw e;
        }
    }
}