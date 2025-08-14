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
    private final Long userID;
    private final Long taskID;

    public static RecommendationResponseDto from(User user, Assignment assignment) {
        return RecommendationResponseDto.builder()
                .userID(user.getId())
                .taskID(assignment.getId())
                .build();
    }
}
