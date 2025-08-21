package heeyoung.hee.global.exception;

public class JwtException extends RuntimeException {
    private final int statusCode;

    public JwtException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
