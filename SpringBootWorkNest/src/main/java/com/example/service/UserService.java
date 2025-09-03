package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Register new user
    public User register(User u) {
        // ❌ Do not encode password, store as plain text
        // u.setPassword(passwordEncoder.encode(u.getPassword()));

        // Assign default role if not set
        if (u.getRole() == null || u.getRole().isEmpty()) {
            u.setRole("ROLE_USER");
        }

        return repo.save(u);
    }

    // Get all users
    public List<User> getAll() {
        return repo.findAll();
    }

    // Find user by id
    public Optional<User> findById(Long id) {
        return repo.findById(id);
    }

    // Delete user
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // Find user by username
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }
}
