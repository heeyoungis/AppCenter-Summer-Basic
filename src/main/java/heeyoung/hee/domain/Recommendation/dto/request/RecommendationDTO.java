package heeyoung.hee.domain.Recommendation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecommendationDTO {
    private Long userID;
    private Long taskID;
}
