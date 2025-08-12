package heeyoung.hee.global.jwt;

import heeyoung.hee.domain.User.service.UserDetailsImpl;
import heeyoung.hee.domain.User.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
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

    @Value("${jwt.access-token-expiration}")
    private Long accessTokenExpiredTime;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiredTime;

    private final Key key; // JWT 서명을 위한 Key 객체 선언
    private final UserDetailsServiceImpl userDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret, UserDetailsServiceImpl userDetailsService){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.userDetailsService = userDetailsService;
    }

    // jwt 생성
    public TokenResponseDto generateToken(String email) {
        String authorities = "USER";

        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(email)
                .claim("auth", authorities)
                .setIssuedAt(now)
                .setExpiration(new Date((now.getTime() + accessTokenExpiredTime)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(email)
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

        // UserDetails 객체 생성 후 Authentication 리턴
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
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
            throw new JwtException("access_token is null");

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtException("토큰이 만료되었습니다.");
        } catch (MalformedJwtException e) {
            throw new JwtException("토큰의 형식이 올바르지 않습니다.");
        } catch (SignatureException | SecurityException e) {
            throw new JwtException("토큰의 서명이 올바르지 않습니다.");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("토큰의 형식이 만료되었습니다.");
        }
    }




}
