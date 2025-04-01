package com.reverie_unique.reverique.domain.answer;

import com.reverie_unique.reverique.domain.dto.QuestionAnswerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public void saveAnswer(Long userId, Long coupleId, Long questionId, String answer) {
        Answer newAnswer = new Answer();
        newAnswer.setCoupleId(coupleId);
        newAnswer.setUserId(userId);
        newAnswer.setQuestionId(questionId);
        newAnswer.setAnswer(answer);  // 아직 답변을 받지 않았으므로 null
        newAnswer.setCreatedAt(LocalDate.now()); // 오늘 날짜로 설정
        newAnswer.setDeleted(0);  // 삭제되지 않은 상태로 설정
        answerRepository.save(newAnswer);  // JPA를 통해 저장
    }
    public boolean updateAnswer(Long id, Answer answer) {
        Optional<Answer> existingAnswerOpt = answerRepository.findById(id);

        if (existingAnswerOpt.isPresent()) {
            Answer existingAnswer = existingAnswerOpt.get();

            existingAnswer.setAnswer(answer.getAnswer());
            answerRepository.save(existingAnswer);  // 업데이트된 엔티티 저장
            return true;
        }

        return false;  // ID에 해당하는 데이터를 찾지 못한 경우
    }

    public boolean deleteAnswer(Long id) {
        Optional<Answer> existingAnswerOpt = answerRepository.findById(id);

        if (existingAnswerOpt.isPresent()) {
            Answer existingAnswer = existingAnswerOpt.get();

            // 답변을 삭제(논리적 삭제) 처리: deleted 필드를 1로 설정
            existingAnswer.setDeleted(1);

            answerRepository.save(existingAnswer);  // 업데이트된 엔티티 저장
            return true;
        }

        return false;  // ID에 해당하는 데이터를 찾지 못한 경우
    }

    public Page<QuestionAnswerResponse> getAnswers(Long userId, Long partnerId, Long coupleId, Pageable pageable){
        Page<QuestionAnswerResponse> answers = answerRepository.getAnswers(userId, partnerId, coupleId, pageable);
        return answers;
    }
}
