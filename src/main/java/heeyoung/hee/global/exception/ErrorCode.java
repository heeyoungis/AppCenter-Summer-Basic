package heeyoung.hee.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 조회에 실패하였습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이메일이 중복됩니다."),

    // Assignment
    ASSIGNMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 과제를 찾을 수 없습니다."),
    INVALID_SORT_OPTION(HttpStatus.BAD_REQUEST, "정렬은 최신순/추천순만 가능합니다."),

    // Recommendation
    USER_NOT_MATCH(HttpStatus.BAD_REQUEST, "추천 취소 권한이 없습니다."),
    ALREADY_RECOMMENDED(HttpStatus.BAD_REQUEST, "이미 추천한 과제입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public String getCode() {
        return this.name();
    }


}
