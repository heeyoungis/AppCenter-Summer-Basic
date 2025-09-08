package heeyoung.hee.domain.Recommendation.service;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.repository.AssignmentRepository;
import heeyoung.hee.domain.Recommendation.dto.response.RecommendationResponseDto;
import heeyoung.hee.domain.Recommendation.entity.Recommendation;
import heeyoung.hee.domain.Recommendation.repository.RecommendationRepository;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final AssignmentRepository assignmentRepository;

    // 과제 추천 추가
    @Transactional
    public RecommendationResponseDto doRecommendation(Long userId, Long assignmentId) {

        // 과제 등록
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 추천 객체 생성
        Recommendation recommendation = Recommendation.create(userId, assignmentId);

        // 과제 추천 카운트 추가
        assignment.increaseRecommendationCount();

        // DB 에 저장
        recommendationRepository.save(recommendation);

        // 응답 DTO 에 담아 반환
        return RecommendationResponseDto.from(userId, assignmentId);
    }

    // 과제 추천 삭제
    @Transactional
    public void deleteRecommendation(Long userId, Long assignmentId) {

        Recommendation recommendation = recommendationRepository.findByUserIdAndAssignmentId(userId,assignmentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 과제 등록 후 추천 카운트 감소
        Assignment assignment = assignmentRepository.findById(assignmentId)
                        .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        assignment.decreaseRecommendationCount();

        recommendationRepository.delete(recommendation);
    }
}
