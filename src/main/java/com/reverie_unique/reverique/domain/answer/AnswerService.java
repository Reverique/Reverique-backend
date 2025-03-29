package com.reverie_unique.reverique.domain.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {  // 생성자명 수정
        this.answerRepository = answerRepository;
    }

    public List<Answer> findTodayAnswers(Long coupleId){
        List<Answer> todayAnswers = answerRepository.findTodayAnswers(coupleId);

        return todayAnswers;
    }
    public void saveAnswer(Long coupleId, Long userId, Long questionId) {
        Answer newAnswer = new Answer();
        newAnswer.setCoupleId(coupleId);
        newAnswer.setUserId(userId);
        newAnswer.setQuestionId(questionId);
        newAnswer.setAnswer(null);  // 아직 답변을 받지 않았으므로 null
        newAnswer.setCreatedAt(LocalDate.now()); // 오늘 날짜로 설정
        newAnswer.setDeleted(0);  // 삭제되지 않은 상태로 설정
        answerRepository.save(newAnswer);  // JPA를 통해 저장
    }
}
