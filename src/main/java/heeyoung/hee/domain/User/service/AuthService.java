package heeyoung.hee.domain.User.service;

import heeyoung.hee.domain.User.dto.request.UserCreateDTO;
import heeyoung.hee.domain.User.dto.request.UserLoginDTO;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import heeyoung.hee.global.jwt.JwtTokenProvider;
import heeyoung.hee.global.jwt.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원가입
    @Transactional
    public User signUp(UserCreateDTO dto) {

        // 이메일 중복 검사
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RestApiException(ErrorCode.DUPLICATE_EMAIL,
                    dto.getEmail()+"은 이미 사용중입니다.");
        }
        else {
            User user = User.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .name(dto.getName())
                    .part(dto.getPart())
                    .gen(dto.getGen())
                    .phoneNumber(dto.getPhoneNumber())
                    .build();
            return userRepository.save(user);
        }
    }

    // 로그인
    @Transactional
    public TokenResponseDto login(UserLoginDTO dto) {
        try {
            // Login ID/PW 기반으로 AuthenticationToken 생성
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

            // 인증 객체 생성되면서 loadUserByUsername 메서드가 실행됨
            Authentication auth = authenticationManagerBuilder.getObject().authenticate(authToken);

            // 인증 정보 기반 jwt 토큰 생성
            return jwtTokenProvider.generateToken(dto.getEmail());

        } catch (BadCredentialsException e) {
            throw new RestApiException(ErrorCode.INVALID_PASSWORD);
        } catch (UsernameNotFoundException e) {
            throw new RestApiException(ErrorCode.USER_NOT_FOUND);
        }
    }

    // 로그아웃
    @Transactional
    public void logout(UserDetailsImpl userDetails) {

        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));
    }
}
