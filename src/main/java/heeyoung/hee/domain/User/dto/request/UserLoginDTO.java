package heeyoung.hee.domain.User.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;
}
