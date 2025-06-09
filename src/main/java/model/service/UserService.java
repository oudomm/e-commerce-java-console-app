package model.service;

import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<UserResponseDto> getAllUser();
    String signUp(UserCreateDto create);
    String login(UserloginDto login);
    void saveUserLog(Map<String,String> map);
    UserloginDto autoLogin();
}
