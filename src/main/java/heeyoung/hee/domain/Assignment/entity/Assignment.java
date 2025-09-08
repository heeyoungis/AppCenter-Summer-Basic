package heeyoung.hee.domain.Assignment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import heeyoung.hee.domain.Recommendation.entity.Recommendation;
import heeyoung.hee.domain.User.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private int recommendationCount = 0;

    // FK 설정
    @Column(name = "user_id")
    private Long userId;

    @Builder
    private Assignment(String title, String content, String link, String createdAt, Long userId) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    public Assignment update (String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
        return this;
    }

    public void increaseRecommendationCount() {
        this.recommendationCount++;
    }

    public void decreaseRecommendationCount() {
        if (this.recommendationCount > 0) {
            this.recommendationCount--;
        }
    }
}
