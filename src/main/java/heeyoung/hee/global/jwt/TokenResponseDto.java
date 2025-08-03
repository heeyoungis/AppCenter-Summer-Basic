package heeyoung.hee.global.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponseDto {
    private final String type;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    private TokenResponseDto(String type, String accessToken, String refreshToken) {
        this.type = "Bearer";
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
