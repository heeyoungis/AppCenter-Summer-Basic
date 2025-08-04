package heeyoung.hee.domain.User.service;

import heeyoung.hee.domain.User.dto.response.UserResponseDTO;
import heeyoung.hee.domain.User.entity.User;
import heeyoung.hee.domain.User.repository.UserRepository;
import heeyoung.hee.global.exception.ErrorCode;
import heeyoung.hee.global.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO getUserInfo(UserDetailsImpl userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RestApiException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDTO.from(user);
    }
}
