package heeyoung.hee.domain.User.service;

import heeyoung.hee.domain.Assignment.dto.response.AssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.Assignment.repository.AssignmentRepository;
import heeyoung.hee.domain.User.dto.response.UserInfoResponseDto;
import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUserInfo(UserDetailsImpl userDetails) {

        // 유저 조회
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        // 과제 조회
        List<Assignment> assignments = assignmentRepository.findByUser(user);
        List<AssignmentResponseDto> assignmentResponseDto =
                assignments.stream()
                .map(AssignmentResponseDto::from)
                .toList();

        return UserInfoResponseDto.from(user, assignmentResponseDto);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findByPart(String part) {
        List<User> users = userRepository.findByPart(part);
        return users.stream()
                .map(UserResponseDTO::from)
                .toList();
    }
}
