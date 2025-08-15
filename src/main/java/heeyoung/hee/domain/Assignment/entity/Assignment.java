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

    // FK 설정
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Recommendation> recommendations = new ArrayList<>();

    @Builder
    private Assignment(String title, String content, String link, String createdAt, User user) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.createdAt = createdAt;
        this.user = user;
        this.recommendations = new ArrayList<>();
    }

    public static Assignment create (String title, String content, String link, String createdAt, User user) {
        Assignment assignment = Assignment.builder()
                .title(title)
                .content(content)
                .link(link)
                .createdAt(createdAt)
                .user(user)
                .build();
        assignment.recommendations = new ArrayList<>();
        return assignment;
    }

    public Assignment update (String title, String content, String link) {
        this.title = title;
        this.content = content;
        this.link = link;
        return this;
    }
}
