package com.worknest.service;

import com.worknest.dao.UserDao;
import com.worknest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(User user) {
        // You would hash the password here for a real application
        userDao.save(user);
    }

    // New method to handle user login
    public User loginUser(String username, String password) {
        User user = userDao.findByUsername(username);
        // For simplicity, we compare plain text passwords.
        // In production, use a password encoder like BCrypt.
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User getUserById(Long userId) {
        return userDao.findById(userId);
    }
    
 // Add this method to your UserService.java file
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
    // New method to delete a user
    public void deleteUser(Long userId) {
        User user = userDao.findById(userId);
        if (user != null) {
            userDao.delete(user);
        }
    }
}