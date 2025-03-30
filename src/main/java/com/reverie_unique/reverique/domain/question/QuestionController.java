package com.reverie_unique.reverique.domain.question;


import com.reverie_unique.reverique.common.ApiResponse;
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
    public ApiResponse<QuestionAnswerResponse> getRandomQuestion() {
        try {
            // 랜덤 질문을 가져오는 서비스 호출
            QuestionAnswerResponse questionAnswerResponse = questionService.getRandomQuestion(1L, 1L);
            if (questionAnswerResponse == null) {
                return new ApiResponse<>("failure", 404, "질문을 찾을 수 없습니다.", null);
            }
            // 정상적인 응답
            return new ApiResponse<>("success", 200, "성공적으로 처리되었습니다.", questionAnswerResponse);
        } catch (Exception e) {
            // 예외 처리 (내부 서버 오류 등)
            return new ApiResponse<>("failure", 500, "서버 오류가 발생했습니다.", null);
        }
    }

}