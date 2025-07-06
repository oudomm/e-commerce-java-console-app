package controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.User;
import model.handelException.InvalidCredentialsException;
import model.handelException.UserAlreadyExistsException;
import model.service.UserService;
import model.service.UserServiceImpl;

import java.util.List;

@NoArgsConstructor
public class UserController {
    private final UserService userService = new UserServiceImpl();
    public List<UserResponseDto> showAllUser(int numberOfRows,int numberOfusers){
        return userService.getAllUser(numberOfRows,numberOfusers);
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
    public User findUserByUuid(String  uuid,int numberOfRole,int numberOfUsers){
        return userService.findUserUuid(uuid,numberOfRole,numberOfUsers);
    }
}