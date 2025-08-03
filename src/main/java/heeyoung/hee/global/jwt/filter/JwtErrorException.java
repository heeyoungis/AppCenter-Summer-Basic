package heeyoung.hee.global.jwt.filter;

public class JwtErrorException extends RuntimeException {
    private final int statusCode;

    public JwtErrorException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
