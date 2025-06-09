package mapper;

import Security.PasswordSecurity;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.entity.User;

import java.util.UUID;

public class UserMapper {
    public static UserResponseDto mapFromUserToUserResponseDto(User user){
        return new UserResponseDto(user.getUUuid(),user.getUserName(),user.getEmail());
    }
    public static User mapFromCreateUseToUser(UserCreateDto create){
        return User.builder()
                .uUuid(UUID.randomUUID().toString())
                .userName(create.name())
                .email(create.email())
                .password(PasswordSecurity.hashing(create.password()))
                .build();
    }
}
