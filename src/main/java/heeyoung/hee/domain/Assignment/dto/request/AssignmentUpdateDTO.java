package heeyoung.hee.domain.Assignment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AssignmentUpdateDTO {
    private String title;
    private String content;
    private String link;
}
