package heeyoung.hee.domain.User.dto.response;

import heeyoung.hee.domain.User.entity.User;
import lombok.*;

@Getter
@Builder
@RequiredArgsConstructor
public class UserResponseDTO {
    private final String email;
    private final String name;
    private final String part;
    private final Double gen;
    private final String phoneNumber;

    public static UserResponseDTO from(User user) {
        return UserResponseDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .part(user.getPart())
                .gen(user.getGen())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
