package heeyoung.hee.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // CustomError 발생시 처리
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponseDto> handleRestApiException(RestApiException e) {
        ErrorCode errorCode = e.getErrorCode();

        // errorcode 기반 ResponseEntity 만들어서 리턴
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponseDto.res(e));
    }
}
