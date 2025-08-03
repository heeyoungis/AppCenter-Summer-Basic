package heeyoung.hee.global.exception;

public class DuplicatedEmail extends CustomException {
    public  DuplicatedEmail(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
