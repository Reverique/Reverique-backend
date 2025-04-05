package com.reverie_unique.reverique.domain.answer;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.constant.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        boolean isUpdated = answerService.updateAnswer(id, answer);
        if (isUpdated) {
            return ApiResponse.success("Answer updated successfully!");
        } else {
            return ApiResponse.failure();
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAnswer(@PathVariable Long id) {
        boolean isDeleted = answerService.deleteAnswer(id);
        if (isDeleted) {
            return ApiResponse.success("Answer deleted successfully!");
        } else {
            return ApiResponse.failure();
        }
    }
}
