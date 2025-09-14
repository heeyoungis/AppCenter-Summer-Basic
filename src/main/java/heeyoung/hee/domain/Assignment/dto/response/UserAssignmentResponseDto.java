package heeyoung.hee.domain.Assignment.dto.response;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserAssignmentResponseDto {
    private final Long id;

    private final String title;

    private final String content;

    private final String link;

    private final String createdAt;

    private final int recommendationCount;

    public UserAssignmentResponseDto(Long id, String title, String content, String link, String createdAt, int recommendationCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.createdAt = createdAt;
        this.recommendationCount = recommendationCount;
    }

    public static UserAssignmentResponseDto from(Assignment assignment, int recommendationCount) {
        return UserAssignmentResponseDto.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .content(assignment.getContent())
                .link(assignment.getLink())
                .createdAt(assignment.getCreatedAt())
                .recommendationCount(recommendationCount)
                .build();
    }
}
