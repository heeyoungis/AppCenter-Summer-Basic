package heeyoung.hee.global.config;

import heeyoung.hee.global.jwt.JwtTokenProvider;
import heeyoung.hee.global.jwt.filter.jwtAuthenticationFilter;
import heeyoung.hee.global.jwt.filter.jwtExceptionFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.authorization.SingleResultAuthorizationManager.permitAll;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider)
            throws Exception {
        http
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(CsrfConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // URL 접근 권한 설정
                .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers(
                                    "/api/users/",
                                    "/api/users/server",
                                    "/api/users/web",
                                    "/api/users/aos",
                                    "/api/users/ios",
                                    "/api/users/Design",
                                    "/api/users/auth/sign-up",
                                    "/api/users/auth/sign-in",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/webjars/**"
                                    ).permitAll()
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
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:5173","https://heeyoung.inuappcenter.kr"));
        corsConfiguration.setExposedHeaders(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
