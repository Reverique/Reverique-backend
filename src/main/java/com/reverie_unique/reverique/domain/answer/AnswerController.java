package com.reverie_unique.reverique.domain.answer;

import com.reverie_unique.reverique.common.ApiResponse;
import com.reverie_unique.reverique.constant.ApiStatus;
import com.reverie_unique.reverique.domain.dto.AnswerUpdateRequest;
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

    @PostMapping("/{id}")
    public ApiResponse<String> createAnswer(@PathVariable Long id, @RequestBody AnswerUpdateRequest request) {
        boolean isUpdated = answerService.updateAnswer(id, request.getAnswer());
        if (isUpdated) {
            return ApiResponse.success("Answer created successfully!");
        } else {
            return ApiResponse.failure();
        }
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateAnswer(@PathVariable Long id, @RequestBody AnswerUpdateRequest request) {
        boolean isUpdated = answerService.updateAnswer(id, request.getAnswer());
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
