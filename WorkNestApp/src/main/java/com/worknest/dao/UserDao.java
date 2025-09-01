package com.worknest.dao;

import com.worknest.model.User;
import java.util.List;

public interface UserDao {
    User findByUsername(String username);
    List<User> findAll();
    User findById(Long id);
    void save(User user);
    void delete(User user); // New method
}