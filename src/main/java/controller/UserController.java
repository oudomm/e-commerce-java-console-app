package controller;

import lombok.RequiredArgsConstructor;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.User;
import model.handelException.InvalidCredentialsException;
import model.handelException.UserAlreadyExistsException;
import model.service.UserService;

import java.util.List;

@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    public List<UserResponseDto> showAllUser(){
        return userService.getAllUser();
    }
    public String signUp(UserCreateDto create) throws UserAlreadyExistsException {
        return userService.signUp(create);
    }
    public String login(UserloginDto login) throws InvalidCredentialsException {
        return userService.login(login);
    }
    public UserloginDto autoLogin(){
        return userService.autoLogin();
    }
    public User findUserByUuid(String  uuid){
        return userService.findUserUuid(uuid);
    }
}