package heeyoung.hee.domain.User.dto.response;

import heeyoung.hee.domain.User.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private String email;
    private String name;
    private String part;
    private Double gen;
    private String phoneNumber;


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
