package heeyoung.hee.domain.User.controller;

import heeyoung.hee.domain.User.dto.request.UserCreateDTO;
import heeyoung.hee.domain.User.dto.request.UserLoginDTO;
import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.domain.User.service.AuthService;
import heeyoung.hee.domain.User.service.UserDetailsImpl;
import heeyoung.hee.domain.User.service.UserService;
import heeyoung.hee.global.jwt.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        /*
        List<User> 를 List<UserResponseDTO> 로 변환하는 과정을 모르겠어요 ㅠ.ㅠ
         */

        return null;
    }

    @GetMapping("/{parts}")
    public List<UserResponseDTO> getPartUsers(@PathVariable String parts) {
        /*
         @PathVariable로 받은 parts 문자열을 어떻게 활용해서 DB에서 해당 part를 가진 사용자만 조회하는 건가요?
         Repository에서 특정 필드(part)로 필터링하려면 메서드를 어떻게 정의해야 되는지 모르겠습니당
         */
        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDTO userResponseDTO = userService.getUserInfo(userDetails);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        User createdUser = authService.signUp(userCreateDTO);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserLoginDTO userLoginDTO) {
        TokenResponseDto loginUserToken = authService.login(userLoginDTO);
        return ResponseEntity.ok(loginUserToken);
    }
}
