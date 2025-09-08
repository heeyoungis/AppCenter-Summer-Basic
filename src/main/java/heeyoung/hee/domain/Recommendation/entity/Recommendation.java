package heeyoung.hee.domain.Recommendation.entity;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "task_id")
    private Long assignmentId;

    @Builder
    private Recommendation(Long userId, Long assignmentId) {
        this.userId = userId;
        this.assignmentId = assignmentId;
    }

    public static Recommendation create(Long userId, Long assignmentId) {
        return Recommendation.builder()
                .userId(userId)
                .assignmentId(assignmentId)
                .build();
    }
}
