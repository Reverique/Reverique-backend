package com.reverie_unique.reverique.domain.question;

import com.reverie_unique.reverique.domain.answer.Answer;
import com.reverie_unique.reverique.domain.answer.AnswerService;
import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
    }

    public QuestionAnswerResponse getRandomQuestion(Long coupleId, Long userId) {
        // 오늘 날짜에 해당하는 답변들을 찾음
        List<Answer> todayAnswers = answerService.findTodayAnswers(coupleId);

        if (todayAnswers.isEmpty()) {
            return getRandomQuestionAndSaveAnswer(coupleId, userId); // 랜덤 질문과 답변 저장 로직을 분리
        } else {
            return getAnswersForToday(coupleId, userId, todayAnswers); // 오늘의 답변 처리 로직을 분리
        }
    }

    // 랜덤 질문을 가져오고 답변을 저장하는 로직
    private QuestionAnswerResponse getRandomQuestionAndSaveAnswer(Long coupleId, Long userId) {
        Optional<Question> randomQuestion = questionRepository.getRandomQuestionExcludingAnswered(coupleId);

        if (randomQuestion.isPresent()) {
            Question question = randomQuestion.get();

            answerService.saveAnswer(coupleId, userId, question.getId()); // AnswerService를 통해 답변 저장

            return new QuestionAnswerResponse(
                    question.getId(),
                    question.getContent(),
                    null,  // 오늘의 답변이 없으므로 null
                    null   // 상대방의 답변도 없음
            );
        } else {
            return null; // 랜덤 질문이 없으면 null 반환
        }
    }

    // 오늘의 답변을 처리하는 로직
    private QuestionAnswerResponse getAnswersForToday(Long coupleId, Long userId, List<Answer> todayAnswers) {
        Answer myAnswer = todayAnswers.stream()
                .filter(answer -> answer.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        Answer otherAnswer = todayAnswers.stream()
                .filter(answer -> !answer.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        Long questionId = todayAnswers.get(0).getQuestionId();
        Optional<Question> todayQuestion = questionRepository.findById(questionId);

        if (todayQuestion.isPresent()) {
            Question question = todayQuestion.get();
            return new QuestionAnswerResponse(
                    question.getId(),
                    question.getContent(),
                    myAnswer != null ? myAnswer.getAnswer() : null,  // 내 답변
                    otherAnswer != null ? otherAnswer.getAnswer() : null  // 상대방의 답변
            );
        } else {
            return null; // 질문이 없을 경우 처리
        }
    }
}
