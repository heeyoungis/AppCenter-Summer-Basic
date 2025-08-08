package heeyoung.hee.domain.Recommendation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RecommendationRequestDto {
    private Long recommendationId;
    private Long userID;
    private Long taskID;
}
