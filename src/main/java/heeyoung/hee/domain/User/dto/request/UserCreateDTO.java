package heeyoung.hee.domain.User.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserCreateDTO {

    @NotBlank(message = "필수 입력 값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
    private String password;

    @NotBlank(message = "필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "필수 입력 값입니다.")
    private String part;

    @NotBlank(message = "필수 입력 값입니다.")
    private Double gen;

    private String phoneNumber;

}
