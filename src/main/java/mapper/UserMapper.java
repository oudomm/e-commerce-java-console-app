package mapper;

import model.dto.UserResponseDto;
import model.entity.User;

public class UserMapper {
    public static UserResponseDto MapFromUserToUserResponseDto(User user) {
        return new UserResponseDto(user.getUserName());
    }
}
