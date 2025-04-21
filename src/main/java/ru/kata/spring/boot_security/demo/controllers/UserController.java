//package ru.kata.spring.boot_security.demo.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import ru.kata.spring.boot_security.demo.repositories.UserRepository;
//import ru.kata.spring.boot_security.demo.models.User;
//
//@Controller
//public class UserController {
//
//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @GetMapping("/user")
//    public String userPage(Model model, Authentication authentication) {
//        String username = authentication.getName();
//        User user = userRepository.findByUsernameWithRoles(username);
//        model.addAttribute("user", user);
//        return "user";
//    }
//}