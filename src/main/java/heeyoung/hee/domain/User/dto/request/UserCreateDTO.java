package heeyoung.hee.domain.User.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserCreateDTO {

    private String email;
    private String password;
    private String name;
    private String part;
    private Double gen;
    private String phoneNumber;

}
