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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Assignment assignment;

    @Builder
    private Recommendation(User user, Assignment assignment) {
        this.user = user;
        this.assignment = assignment;
    }

    public static Recommendation create(User user, Assignment assignment) {
        return Recommendation.builder()
                .user(user)
                .assignment(assignment)
                .build();
    }
}
