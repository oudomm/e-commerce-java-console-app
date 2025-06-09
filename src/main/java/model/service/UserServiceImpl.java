package model.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mapper.UserMapper;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.User;
import model.repository.UserRepository;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();
    private static final String USERS_FILE = "src/main/java/model/file/userlogs/users.json";
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public List<UserResponseDto> getAllUser() {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        userRepository.findAll().forEach(e->userResponseDtos.add(UserMapper.mapFromUserToUserResponseDto(e)));
        return userResponseDtos;
    }

    @Override
    public String signUp(UserCreateDto create) {
        User user = userRepository.findUserByEmail(create.email());
        if (user == null){
            userRepository.save(UserMapper.mapFromCreateUseToUser(create));
            return user.getUUuid();
        }else System.out.println("User is already exist");
        return null;
    }
    @Override
    public String login(UserloginDto login) {
        User user = userRepository.findUserByEmail(login.email());
        if (user != null){
            if (user.getEmail().equals(login.email())){
                if(user.getPassword() == login.password()){
                    user.setStatus(true);
                    Map<String,String > map = new TreeMap<>();
                    map.put("email", user.getEmail());
                    map.put("password",user.getPassword());
                    map.put("status",user.getStatus().toString());
                    saveUserLog(map);
                    return user.getUUuid();
                }
            }
        }
        return null;
    }
    @Override
    public void saveUserLog(Map<String,String> map) {
        File file =new File(USERS_FILE);
        try{
            file.createNewFile();
            mapper.writeValue(file,map);
        }catch (Exception e){
            System.out.println(
                    e.getMessage()
            );
        }
    }
    @Override
    public UserloginDto autoLogin() {
        Map<String ,String > user = readDate();
        return new UserloginDto(user.get("email"),user.get("password"),Boolean.valueOf(user.get("status")));
    }
    private Map<String ,String > readDate(){
        try{
            return mapper.readValue(USERS_FILE, new TypeReference<Map<String, String>>() {});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
