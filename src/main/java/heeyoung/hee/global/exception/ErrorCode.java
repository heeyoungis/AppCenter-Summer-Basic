package heeyoung.hee.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 조회에 실패하였습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이메일이 중복됩니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public String getCode() {
        return this.name();
    }


}
