package com.reverie_unique.reverique.domain.question;


import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.common.PageInfo;
import com.reverie_unique.reverique.constant.ApiStatus;
import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @GetMapping("/received")
    public ApiResponse<List<QuestionAnswerResponse>> getAnswers(@RequestParam Long userId, @RequestParam Long coupleId,
                                                                @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<QuestionAnswerResponse> responses = questionService.getAnswers(userId, coupleId, pageable);
            PageInfo pageInfo = new PageInfo(
                    responses.getTotalPages(),
                    responses.getTotalElements(),
                    responses.getSize(),
                    responses.getNumber()
            );
            return new ApiResponse<>(ApiStatus.SUCCESS, ApiStatus.STATUS_OK, "Success", responses.getContent(), pageInfo);
        } catch (Exception e) {
            throw e;
        }
    }
}