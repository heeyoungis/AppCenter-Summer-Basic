package heeyoung.hee.global.jwt.filter;

import heeyoung.hee.global.jwt.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class jwtAuthenticationFilter extends OncePerRequestFilter {

    // jwt 토큰을 처리하기 위한 JwtTokenProvider 객체
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
        throws ServletException, IOException {
            try {
                // Request Header 에서 jwt 토큰 추출
                String token = getTokenFromRequest(req);

                // 토큰 유효성 검사
                if (jwtTokenProvider.validateToken(token)) {
                    // 토큰이 유효하다면 인증 객체 생성
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);
                    // SecurityContext 에 인증 객체 생성
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                // 다음 필터로 이동
                filterChain.doFilter(req, res);
            }
            catch (JwtException e) {
                throw e;
            }
        }

        private String getTokenFromRequest(HttpServletRequest req) {
            String token = req.getHeader("Authorization");
            if (token == null) {
                // 토큰이 없으면 null 반환
                return null;
            }
            return token.substring(7);
    }

}
