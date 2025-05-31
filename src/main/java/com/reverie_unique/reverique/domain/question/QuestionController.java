package com.reverie_unique.reverique.domain.question;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.common.PageInfo;
import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    // 매일 질문 조회
    @GetMapping("/daily-question")
    public ApiResponse<QuestionAnswerResponse> getRandomQuestion(@RequestParam Long userId, @RequestParam Long coupleId) {
        QuestionAnswerResponse questionAnswerResponse = questionService.getDailyQuestion(userId, coupleId);
        if (questionAnswerResponse == null) {
            return ApiResponse.failure();
        }
        return ApiResponse.success(questionAnswerResponse);
    }

    // 받은 답변 목록 조회
    @GetMapping("/received")
    public ApiResponse<List<QuestionAnswerResponse>> getAnswers(@RequestParam Long userId,
                                                                @RequestParam Long coupleId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionAnswerResponse> responses = questionService.getAnswers(userId, coupleId, pageable);

        PageInfo pageInfo = new PageInfo(
                responses.getTotalPages(),
                responses.getTotalElements(),
                responses.getSize(),
                responses.getNumber()
        );

        return ApiResponse.success(responses.getContent(), pageInfo);
    }
}
