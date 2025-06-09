package model.repository;

import model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User,Integer>{
    @Override
    public User save(User user) {
        String sql = """
        INSERT INTO users ( u_uuid, user_name, email, password)
        values (?,?,?,?)
        """;
        try(Connection con = DriverManager.getConnection("jdbc:postgresql://35.224.242.247:5432/postgres","postgres","houygood@123")){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,user.getUUuid());
            statement.setString(2,user.getUserName());
            statement.setString(3,user.getEmail());
            statement.setString(4,user.getPassword());
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0 ? user : null;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    @Override
    public List<User> findAll() {
        String sql = """
                SELECT * FROM users
                WHERE is_delete = false
                """;
        try(Connection con = DriverManager.getConnection("jdbc:postgresql://35.224.242.247:5432/postgres","postgres","houygood@123")){
            Statement statement = con.createStatement();
            List<User> userList =new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUUuid(resultSet.getString("u_uuid"));
                user.setUserName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("is_deleted"));
                userList.add(user);
            }
            return userList;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    @Override
    public Integer delete(Integer id) {
        String  sql= """
                UPDATE users
                SET is_deleted = true
                WHERE id = ?
                """;
        try(Connection con = DriverManager.getConnection("jdbc:postgresql://35.224.242.247:5432/postgres","postgres","houygood@123")){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,id);
            int rowAffected = statement.executeUpdate();
            return rowAffected > 0 ? id : null;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public User findUserByEmail(String email){
        String sql = """
                SELECT * FROM users
                WHERE email = ?
                """;
        try(Connection con = DriverManager.getConnection("jdbc:postgresql://35.224.242.247:5432/postgres","postgres","houygood@123")){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setUUuid(resultSet.getString("u_uuid"));
                user.setUserName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("is_deleted"));
            }
            return user;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  null;
    }
    public User findUserByUuid(String uuid){
        String sql = """
                SELECT * FROM users
                WHERE u_uuid = ?
                """;
        try(Connection con = DriverManager.getConnection("jdbc:postgresql://35.224.242.247:5432/postgres","postgres","houygood@123")){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1,uuid);
            ResultSet resultSet = statement.executeQuery();
            User user = new User();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setUUuid(resultSet.getString("u_uuid"));
                user.setUserName(resultSet.getString("user_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("is_deleted"));
            }
            return user;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  null;
    }
}
