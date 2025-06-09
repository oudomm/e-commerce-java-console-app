package controller;

import lombok.RequiredArgsConstructor;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.service.UserService;

import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    public List<UserResponseDto> showAllUser(){
        return userService.getAllUser();
    }
    public String signUp(UserCreateDto create){
        return userService.signUp(create);
    }
    public String login(UserloginDto login){
        return userService.login(login);
    }
    public UserloginDto autoLogin(){
        return userService.autoLogin();
    }
}
