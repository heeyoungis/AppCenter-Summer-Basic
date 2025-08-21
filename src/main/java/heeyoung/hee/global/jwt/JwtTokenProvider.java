package heeyoung.hee.global.jwt;

import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import heeyoung.hee.domain.User.service.UserDetailsServiceImpl;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.JwtException;
import heeyoung.hee.global.exception.RestApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final UserRepository userRepository;
    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiredTime;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiredTime;

    private final Key key; // JWT 서명을 위한 Key 객체 선언
    private final UserDetailsServiceImpl userDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, UserDetailsServiceImpl userDetailsService, UserRepository userRepository){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    // jwt 생성
    public TokenResponseDto generateToken(User user) {
        String authorities = "USER";

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + accessTokenExpiredTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + accessTokenExpiredTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // jwt 토큰을 복호화해 토큰에 들어있는 정보를 꺼내 Authentication 객체를 생성하는 메서드
    public Authentication getAuthentication(String token) {
        // jwt 토큰에서 사용자 정보 추출
        Claims claims = getClaims(token);

        // 권한 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        Long id = Long.parseLong(claims.getSubject());

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        // UserDetails 객체 생성 후 Authentication 리턴
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody(); // 페이로드 (Claims) 리턴
    }

    // jwt 토큰 유효성 검증 메서드
    public boolean validateToken(String accessToken) {
        if (accessToken == null)
            return false;

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new heeyoung.hee.global.exception.JwtException("토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            throw new heeyoung.hee.global.exception.JwtException("토큰의 형식이 올바르지 않습니다.");
        } catch (SignatureException | SecurityException e) {
            throw new heeyoung.hee.global.exception.JwtException("토큰의 서명이 올바르지 않습니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("토큰의 형식이 만료되었습니다.");
        }
    }

}
