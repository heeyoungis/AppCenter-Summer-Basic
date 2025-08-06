package heeyoung.hee.domain.User.service;

import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDTO::from)
                .toList();
    }

    public UserResponseDTO getUserInfo(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDTO.from(user);
    }

    public List<UserResponseDTO> findByPart(String part) {
        List<User> users = userRepository.findByPart(part);
        return users.stream()
                .map(UserResponseDTO::from)
                .toList();
    }
}
