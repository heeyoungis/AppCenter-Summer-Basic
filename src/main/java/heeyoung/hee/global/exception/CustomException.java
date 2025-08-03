package heeyoung.hee.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    protected ErrorCode errorCode;
    protected String detail;

    // detail 전달 x
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // detail 전달 o
    public CustomException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = detail;
    }
}

