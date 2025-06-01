package com.reverie_unique.reverique.domain.question;

import com.reverie_unique.reverique.domain.answer.Answer;
import com.reverie_unique.reverique.domain.answer.AnswerService;
import com.reverie_unique.reverique.domain.question.dto.QuestionAnswerResponse;
import com.reverie_unique.reverique.domain.user.entity.User;
import com.reverie_unique.reverique.domain.user.service.UserService;
import com.reverie_unique.reverique.infrastructure.redis.service.DailyQuestionRedisService;
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
    private final DailyQuestionRedisService dailyQuestionRedisService;
    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerService answerService, UserService userService, DailyQuestionRedisService dailyQuestionRedisService) {
        this.questionRepository = questionRepository;
        this.answerService = answerService;
        this.userService = userService;
        this.dailyQuestionRedisService = dailyQuestionRedisService;
    }

    private String getRedisKey(Long coupleId) {
        return "daily_question:" + coupleId;
    }

    public QuestionAnswerResponse getDailyQuestion(Long userId, Long coupleId) {
        User user = userService.findById(userId);
        if (user == null) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다.");
        }
        if (!Objects.equals(user.getCoupleId(), coupleId)) {
            throw new IllegalArgumentException("이 사용자는 해당 커플에 속하지 않습니다.");
        }

        String redisKey = getRedisKey(coupleId);

        // 레디스에 오늘 질문이 있으면 가져오기
        Object cachedQuestion = dailyQuestionRedisService.getQuestion(redisKey);
        if (cachedQuestion != null && cachedQuestion instanceof Question) {
            Question question = (Question) cachedQuestion;

            // 오늘 답변이 이미 있으면 답변 반환
            List<Answer> todayAnswers = answerService.findTodayAnswers(coupleId);
            if (!todayAnswers.isEmpty()) {
                return getAnswersForToday(userId, todayAnswers, question);
            } else {
                // 답변이 없으면 답변 저장
                saveAnswersForUsers(userId, coupleId, question.getId());
                return new QuestionAnswerResponse(
                        question.getId(),
                        question.getContent(),
                        null,
                        null,
                        null);
            }
        }

        // 레디스에 없으면 DB에서 랜덤 질문 가져오기
        Optional<Question> randomQuestionOpt = questionRepository.getRandomQuestionExcludingAnswered(coupleId);
        if (randomQuestionOpt.isEmpty()) {
            return null;
        }
        Question randomQuestion = randomQuestionOpt.get();

        // Redis에 저장 (TTL은 자정까지)
        dailyQuestionRedisService.saveQuestion(redisKey, randomQuestion);

        // 답변 저장
        saveAnswersForUsers(userId, coupleId, randomQuestion.getId());

        return new QuestionAnswerResponse(
                randomQuestion.getId(),
                randomQuestion.getContent(),
                null,
                null,
                null);
    }


    private void saveAnswersForUsers(Long userId, Long coupleId, Long questionId) {
        answerService.saveAnswer(userId, coupleId, questionId, null);
        Long partnerId = getPartnerId(userId, coupleId);
        answerService.saveAnswer(partnerId, coupleId, questionId, null);
    }

    private Long getPartnerId(Long userId, Long coupleId) {
        List<User> usersInCouple = userService.getUsersByCoupleId(coupleId);
        return usersInCouple.stream()
                .filter(user -> !user.getId().equals(userId))
                .findFirst()
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("No other user found for coupleId: " + coupleId));
    }

    private QuestionAnswerResponse getAnswersForToday(Long userId, List<Answer> todayAnswers, Question question) {
        Answer myAnswer = todayAnswers.stream()
                .filter(answer -> answer.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        Answer otherAnswer = todayAnswers.stream()
                .filter(answer -> !answer.getUserId().equals(userId))
                .findFirst()
                .orElse(null);

        return new QuestionAnswerResponse(
                question.getId(),
                question.getContent(),
                myAnswer != null ? myAnswer.getAnswer() : null,
                otherAnswer != null ? otherAnswer.getAnswer() : null,
                null);
    }

    public Page<QuestionAnswerResponse> getAnswers(Long userId, Long coupleId, Pageable pageable) {
        Long partnerId = getPartnerId(userId, coupleId);
        return answerService.getAnswers(userId, partnerId, coupleId, pageable);
    }
}
