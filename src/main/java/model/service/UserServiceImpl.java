package model.service;

import Security.PasswordSecurity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mapper.UserMapper;
import model.dto.UserCreateDto;
import model.dto.UserResponseDto;
import model.dto.UserloginDto;
import model.entity.User;
import model.handelException.InvalidCredentialsException;
import model.handelException.UserAlreadyExistsException;
import model.repository.UserRepository;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class UserServiceImpl implements UserService {
    private final UserRepository userRepository = new UserRepository();
    private static final Path USERS_FILE = Paths.get("src", "main", "java", "model", "file", "userlogs", "users.json");
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public List<UserResponseDto> getAllUser() {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        userRepository.findAll().forEach(e->userResponseDtos.add(UserMapper.mapFromUserToUserResponseDto(e)));
        return userResponseDtos;
    }

    @Override
    public String signUp(UserCreateDto create) throws UserAlreadyExistsException {
        User existingUser = userRepository.findUserByEmail(create.email());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + create.email());
        }

        User newUser = UserMapper.mapFromCreateUseToUser(create);
        User savedUser = userRepository.save(newUser);

        // Return UUID for consistency with login method
        return savedUser.getUUuid();
    }

    @Override
    public String login(UserloginDto login) throws InvalidCredentialsException {
        User user = userRepository.findUserByEmail(login.email());
        if (user != null) {
            String hashedInputPassword = PasswordSecurity.hashing(login.password());

            if (user.getPassword().equals(hashedInputPassword)) {
                user.setStatus(true);
                Map<String, String> map = new TreeMap<>();
                map.put("email", user.getEmail());
                map.put("password", user.getPassword());
                map.put("status", user.getStatus().toString());
                saveUserLog(map);
                return user.getUUuid();
            } else {
                throw new InvalidCredentialsException("Password is incorrect.");
            }
        } else {
            throw new InvalidCredentialsException("User with email " + login.email() + " not found.");
        }
    }
    @Override
    public void saveUserLog(Map<String,String> map) {
        File file =new File(USERS_FILE.toFile().toURI());
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
            return mapper.readValue(USERS_FILE.toFile(), new TypeReference<Map<String, String>>() {});
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public User findUserUuid(String uuid){
        Integer id = userRepository.findAll().stream().filter(user -> user.getUUuid().equals(uuid)).findFirst().get().getId();
        return userRepository.findUserById(id);
    }
}
