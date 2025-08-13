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

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final AssignmentRepository assignmentRepository;

    // 과제 추천 추가
    public RecommendationResponseDto doRecommendation(User user, Long assignmentId) {

        // 과제 등록
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 추천 객체 생성
        Recommendation recommendation = Recommendation.create(user, assignment);

        // DB 에 저장
        recommendationRepository.save(recommendation);

        // 응답 DTO 에 담아 반환
        return RecommendationResponseDto.from(user, assignment);
    }

    // 과제 추천 삭제
    public void deleteRecommendation(User user, Long assignmentId) {

        Recommendation recommendation = recommendationRepository.findByUserIdAndAssignmentId(user.getId(),assignmentId)
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 삭제를 요청한 User 와 조회한 Recommendation의 userId가 동일하지 않을 경우
        if (!recommendation.getUser().getId().equals(user.getId())) {
            throw new RestApiException(ErrorCode.USER_NOT_MATCH);
        }

        recommendationRepository.delete(recommendation);
    }
}
