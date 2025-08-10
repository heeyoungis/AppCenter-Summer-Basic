package heeyoung.hee.domain.User.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "필수 입력 값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{8,16}$", message = "비밀번호는 8~16자의 영문 대소문자, 숫자, 특수문자로 이루어져야 합니다.")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "필수 입력 값입니다.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "필수 입력 값입니다.")
    private String part;

    @Column(nullable = false)
    @NotBlank(message = "필수 입력 값입니다.")
    private Double gen;

    @Column(nullable = false, name = "phone_num")
    private String phoneNumber;

    @Builder
    private User(String email, String password, String name, String part, Double gen, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.part = part;
        this.gen = gen;
        this.phoneNumber = phoneNumber;
    }

    public static User create(String email, String password, String name, String part, Double gen, String phoneNumber) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .part(part)
                .gen(gen)
                .phoneNumber(phoneNumber)
                .build();
    }
}
