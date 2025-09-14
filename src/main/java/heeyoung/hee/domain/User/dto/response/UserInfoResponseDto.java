package heeyoung.hee.domain.User.dto.response;

import heeyoung.hee.domain.Assignment.dto.response.UserAssignmentResponseDto;
import heeyoung.hee.domain.Assignment.entity.Assignment;
import heeyoung.hee.domain.User.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class UserInfoResponseDto {
    private final String name;
    private final String part;
    private final Double gen;
    private final List<UserAssignmentResponseDto> assignments;

    public static UserInfoResponseDto from(User user, List<UserAssignmentResponseDto> assignments) {
        return UserInfoResponseDto.builder()
                .name(user.getName())
                .part(user.getPart())
                .gen(user.getGen())
                .assignments(assignments)
                .build();
    }
}
