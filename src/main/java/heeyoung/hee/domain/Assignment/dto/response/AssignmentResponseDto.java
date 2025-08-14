package heeyoung.hee.domain.Assignment.dto.response;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
public class AssignmentResponseDto {

    private final String title;

    private final String content;

    private final String link;

    private final String createdAt;

    public static AssignmentResponseDto from(Assignment assignment) {
        return AssignmentResponseDto.builder()
                .title(assignment.getTitle())
                .content(assignment.getContent())
                .link(assignment.getLink())
                .createdAt(assignment.getCreatedAt())
                .build();
    }
}
