package heeyoung.hee.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class RestApiException extends CustomException {
    public RestApiException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RestApiException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
