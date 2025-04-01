package com.reverie_unique.reverique.domain.dto;

import java.time.LocalDate;

public class QuestionAnswerResponse {
    private Long questionId;
    private String content;
    private String answer1;  // 내가 답한 답변
    private String answer2;  // 상대방이 답한 답변
    private LocalDate createdAt;



    public QuestionAnswerResponse(Long questionId, String content, String answer1, String answer2, LocalDate createdAt) {
        this.questionId = questionId;
        this.content = content;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "QuestionAnswerResponse{" +
                "questionId=" + questionId +
                ", content='" + content + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", date='" + createdAt +'\'' +
                '}';
    }
}