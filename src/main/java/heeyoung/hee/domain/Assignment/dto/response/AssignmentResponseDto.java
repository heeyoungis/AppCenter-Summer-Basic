package heeyoung.hee.domain.Assignment.dto.response;

import heeyoung.hee.domain.Assignment.entity.Assignment;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AssignmentResponseDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String link;

    private String createdAt;

    public static AssignmentResponseDto from(Assignment assignment) {
        return AssignmentResponseDto.builder()
                .title(assignment.getTitle())
                .content(assignment.getContent())
                .link(assignment.getLink())
                .createdAt(assignment.getCreatedAt())
                .build();
    }
}
