package heeyoung.hee.domain.User.service;

import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Assignment.dto.response.UserAssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.repository.AssignmentRepository;
import heeyoung.hee.domain.User.dto.request.UserUpdateDto;
import heeyoung.hee.domain.User.dto.response.UserInfoResponseDto;
import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;
    private final  PasswordEncoder passwordEncoder;

    // 유저 전체 조회
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAllUsers();
    }

    // 마이페이지 조회
    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(UserDetailsImpl userDetails) {

        // 유저 조회
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        // 과제 조회
        List<UserAssignmentResponseDto> assignments = assignmentRepository.findUserAssignments(user.getId());

        return UserInfoResponseDto.from(user, assignments);
    }

    // 파트별 조회
    @Transactional(readOnly = true)
    public List<UserResponseDTO> findByPart(String part) {
        return userRepository.findAllByPart(part);
    }

    // 유저 정보 수정
    @Transactional
    public UserResponseDTO updateUser(UserUpdateDto dto, UserDetailsImpl userDetails) {

        // 유저 조회
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 암호화
        String password = passwordEncoder.encode(dto.getPassword());

        user.update(
                dto.getEmail(),
                password,
                dto.getName(),
                dto.getPart(),
                dto.getGen(),
                dto.getPhoneNumber()
        );

        return UserResponseDTO.from(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(UserDetailsImpl userDetails) {

        // 유저 조회
        User user = userRepository.findById(userDetails.getUser().getId())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

}
