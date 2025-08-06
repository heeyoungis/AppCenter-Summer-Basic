package heeyoung.hee.domain.Assignment.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AssignmentSubmitDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String link;
}
