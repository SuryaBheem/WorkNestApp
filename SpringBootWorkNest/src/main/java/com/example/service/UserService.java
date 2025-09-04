package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }

    public User register(User u) {
        if (u.getRole() == null || u.getRole().isEmpty()) u.setRole("ROLE_USER");
        return repo.save(u);
    }

    public List<User> getAll() { return repo.findAll(); }
    public Optional<User> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
    public Optional<User> findByUsername(String username) { return repo.findByUsername(username); }
    public User updateUser(User u) { return repo.save(u); }
}
