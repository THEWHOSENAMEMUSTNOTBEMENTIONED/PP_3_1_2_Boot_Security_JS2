package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserRESTController {

    private final UserRepository userRepository;

    @Autowired
    public UserRESTController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<User> getAuthUser(Authentication authentication) {
        String username = authentication.getName();
        User currentUser = userRepository.findByUsernameWithRoles(username);
        return currentUser != null ? ResponseEntity.ok(currentUser) : ResponseEntity.notFound().build();
    }
}