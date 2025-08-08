package heeyoung.hee.domain.Recommendation.dto.response;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationResponseDto {
    private Long userID;
    private Long taskID;

    public static RecommendationResponseDto from(User user, Assignment assignment) {
        return new RecommendationResponseDto(user.getId(), assignment.getId());
    }
}
