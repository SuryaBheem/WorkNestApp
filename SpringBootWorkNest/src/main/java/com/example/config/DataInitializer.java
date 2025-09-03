package com.example.config;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    public DataInitializer(UserService userService){ this.userService = userService; }

    @Override
    public void run(String... args) throws Exception {
        if(userService.findByUsername("admin").isEmpty()){
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            userService.register(admin);
        }

        if(userService.findByUsername("user").isEmpty()){
            User u = new User();
            u.setUsername("user");
            u.setPassword("user123");
            u.setRole("USER");
            userService.register(u);
        }
    }
}
