package heeyoung.hee.global.config;

import heeyoung.hee.global.jwt.JwtTokenProvider;
import heeyoung.hee.global.jwt.filter.jwtAuthenticationFilter;
import heeyoung.hee.global.jwt.filter.jwtExceptionFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider)
            throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // URL 접근 권한 설정
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(
                                "/api/users/sign-up",
                                "/api/users/sign-in",
                                "/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 세션 관리 설정
                .sessionManagement((sessionManagement) -> sessionManagement
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                )

                //필터 등록
                .addFilterBefore(new jwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new jwtExceptionFilter(), jwtAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
