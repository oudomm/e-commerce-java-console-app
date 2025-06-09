package model.service;

import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.User;
import model.handelException.InvalidCredentialsException;
import model.handelException.UserAlreadyExistsException;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponseDto> getAllUser();
    String signUp(UserCreateDto create) throws UserAlreadyExistsException;
    String login(UserloginDto login) throws InvalidCredentialsException;
    void saveUserLog(Map<String,String> map);
    UserloginDto autoLogin();
    User findUserUuid(String uuid);
}
