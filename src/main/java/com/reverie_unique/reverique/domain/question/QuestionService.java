package com.reverie_unique.reverique.domain.question;

import com.reverie_unique.reverique.domain.answer.Answer;
import com.reverie_unique.reverique.domain.answer.AnswerService;
import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import com.reverie_unique.reverique.domain.user.User;
import com.reverie_unique.reverique.domain.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final UserService userService;
    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerService answerService, UserService userService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
        this.userService = userService;
    }

    public QuestionAnswerResponse getRandomQuestion(Long userId, Long coupleId) {

        User user = userService.findById(userId); // userId로 사용자 정보 조회
        if (user == null) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다."); // EntityNotFoundException 던지기
        }
        if (!Objects.equals(user.getCoupleId(), coupleId)) {
            throw new IllegalArgumentException("이 사용자는 해당 커플에 속하지 않습니다.");
        }

        // 오늘 날짜에 해당하는 답변들을 찾음
        List<Answer> todayAnswers = answerService.findTodayAnswers(coupleId);

        if (todayAnswers.isEmpty()) {
            return getRandomQuestionAndSaveAnswer(userId, coupleId); // 랜덤 질문과 답변 저장 로직을 분리
        } else {
            return getAnswersForToday(userId, todayAnswers); // 오늘의 답변 처리 로직을 분리
        }
    }

    // 랜덤 질문을 가져오고 답변을 저장하는 로직
    private QuestionAnswerResponse getRandomQuestionAndSaveAnswer(Long userId, Long coupleId) {
        Optional<Question> randomQuestion = questionRepository.getRandomQuestionExcludingAnswered(coupleId);

        if (randomQuestion.isPresent()) {
            Question question = randomQuestion.get();

            answerService.saveAnswer(userId, coupleId, question.getId(), null); // AnswerService를 통해 답변 저장
            Long otherUserId = getPartnerId(userId, coupleId);

            answerService.saveAnswer(otherUserId, coupleId, question.getId(), null);


            return new QuestionAnswerResponse(
                    question.getId(),
                    question.getContent(),
                    null,  // 오늘의 답변이 없으므로 null
                    null ,  // 상대방의 답변도 없음,
                    null
            );
        } else {
            return null; // 랜덤 질문이 없으면 null 반환
        }
    }

    private Long getPartnerId(Long userId, Long coupleId) {
        // coupleId에 해당하는 두 사람을 찾고, 현재 userId와 다른 userId를 반환
        List<User> usersInCouple = userService.getUsersByCoupleId(coupleId);
        return usersInCouple.stream()
                .filter(user -> !user.getId().equals(userId)) // userId와 다른 사람을 찾음
                .findFirst()
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("No other user found for coupleId: " + coupleId));
    }

    // 오늘의 답변을 처리하는 로직
    private QuestionAnswerResponse getAnswersForToday(Long userId, List<Answer> todayAnswers) {
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
                    otherAnswer != null ? otherAnswer.getAnswer() : null,  // 상대방의 답변
                    null);
        } else {
            return null; // 질문이 없을 경우 처리
        }
    }
    public Page<QuestionAnswerResponse> getAnswers(Long userId, Long coupleId, Pageable pageable) {
        Long partnerId = getPartnerId(userId, coupleId);
        return answerService.getAnswers(userId, partnerId, coupleId, pageable);
    }
}
