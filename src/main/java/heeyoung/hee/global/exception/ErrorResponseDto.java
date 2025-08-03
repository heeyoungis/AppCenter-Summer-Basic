package heeyoung.hee.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private final String errorcode;
    private final String message;
    private final String detail;

    public static ErrorResponseDto res(final CustomException exception) {
        String errorcode = exception.getErrorCode().getCode();
        String message = exception.getMessage();
        String detail = exception.getDetail();
        return new ErrorResponseDto(errorcode, message, detail);
    }

    public static ErrorResponseDto res(final String errorcode, final Exception exception) {
        return new ErrorResponseDto(errorcode, exception.getMessage(), null);
    }
}
