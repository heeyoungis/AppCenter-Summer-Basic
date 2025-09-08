package heeyoung.hee.domain.Assignment.dto.response;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import lombok.*;

@Getter
@Builder
public class AssignmentResponseDto {

    private final Long id;

    private final String title;

    private final String content;

    private final String link;

    private final String createdAt;

    private final int recommendationCount;

    public AssignmentResponseDto(Long id, String title, String content, String link, String createdAt, int recommendationCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.link = link;
        this.createdAt = createdAt;
        this.recommendationCount = recommendationCount;
    }

    public static AssignmentResponseDto from(Assignment assignment) {
        return AssignmentResponseDto.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .content(assignment.getContent())
                .link(assignment.getLink())
                .createdAt(assignment.getCreatedAt())
                .build();
    }

    public static AssignmentResponseDto from(Assignment assignment, int recommendationCount) {
        return AssignmentResponseDto.builder()
                .id(assignment.getId())
                .title(assignment.getTitle())
                .content(assignment.getContent())
                .link(assignment.getLink())
                .createdAt(assignment.getCreatedAt())
                .recommendationCount(recommendationCount)
                .build();
    }
}
