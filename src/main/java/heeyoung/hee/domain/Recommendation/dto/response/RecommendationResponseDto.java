package heeyoung.hee.domain.Recommendation.dto.response;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class RecommendationResponseDto {
    private final Long userId;
    private final Long taskId;
    private final boolean isRecommended;

    public static RecommendationResponseDto from(Long userId, Long assignmentId) {
        return RecommendationResponseDto.builder()
                .userId(userId)
                .taskId(assignmentId)
                .build();
    }
}
