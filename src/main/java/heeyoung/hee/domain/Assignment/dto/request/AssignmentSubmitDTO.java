package heeyoung.hee.domain.Assignment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentSubmitDTO {

    @Schema(example = "TITLE")
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @Schema(example = "CONTENT")
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    @Schema(example = "http://example/assignment1")
    @NotBlank(message = "링크는 필수 입력 값입니다.")
    private String link;
}
