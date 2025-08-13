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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController implements UserApiSpecification {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{parts}")
    public ResponseEntity<List<UserResponseDTO>> getPartUsers(@PathVariable String parts) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByPart(parts));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDTO userResponseDTO = userService.getUserInfo(userDetails);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User createdUser = authService.signUp(userCreateDTO);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserLoginDTO userLoginDTO) {
        TokenResponseDto loginUserToken = authService.login(userLoginDTO);
        return ResponseEntity.ok(loginUserToken);
    }
}
