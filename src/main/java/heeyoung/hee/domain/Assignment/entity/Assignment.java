package heeyoung.hee.domain.Assignment.entity;

import heeyoung.hee.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskID;

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

    @Builder
    public Assignment(String title, String content, String link, String createdAt, User user) {
        this.title = title;
        this.content = content;
        this.link = link;
        this.createdAt = createdAt;
        this.user = user;
    }

    public static Assignment create (String title, String content, String link, String createdAt, User user) {
        return Assignment.builder()
                .title(title)
                .content(content)
                .link(link)
                .createdAt(createdAt)
                .build();
    }

}
