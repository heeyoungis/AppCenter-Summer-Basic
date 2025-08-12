package heeyoung.hee.domain.Recommendation.service;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.repository.AssignmentRepository;
import heeyoung.hee.domain.Recommendation.dto.request.RecommendationRequestDto;
import heeyoung.hee.domain.Recommendation.dto.response.RecommendationResponseDto;
import heeyoung.hee.domain.Recommendation.entity.Recommendation;
import heeyoung.hee.domain.Recommendation.repository.RecommendationRepository;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {

    private RecommendationRepository recommendationRepository;
    private UserRepository userRepository;
    private AssignmentRepository assignmentRepository;

    // 과제 추천 추가
    public RecommendationResponseDto doRecommendation(RecommendationRequestDto requestDto) {

        // 유저 등록
        User user = userRepository.findById(requestDto.getUserID())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        // 과제 등록
        Assignment assignment = assignmentRepository.findById(requestDto.getTaskID())
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        // 추천 객체 생성
        Recommendation recommendation = Recommendation.create(user, assignment);

        // DB 에 저장
        recommendationRepository.save(recommendation);

        // 응답 DTO 에 담아 반환
        return RecommendationResponseDto.from(user, assignment);
    }

    // 과제 추천 삭제
    public void deleteRecommendation(RecommendationRequestDto requestDto) {

        Recommendation recommendation = recommendationRepository.findById(requestDto.getRecommendationId())
                .orElseThrow(() -> new RestApiException(ErrorCode.ASSIGNMENT_NOT_FOUND));

        recommendationRepository.delete(recommendation);
    }
}
