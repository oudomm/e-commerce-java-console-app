package model.repository;

import model.entity.User;

import java.util.List;

public class UserRepository implements Repository<User,Integer> {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Integer delete(Integer id) {
        return 0;
    }
    public User findUserById(Integer id) {
        return null;
    }
}
