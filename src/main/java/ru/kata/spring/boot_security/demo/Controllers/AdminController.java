package ru.kata.spring.boot_security.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import ru.kata.spring.boot_security.demo.Services.UserService;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    UserService userService;
    UserRepository userRepository;

    @Autowired
    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //получаем всех пользователей
    @GetMapping("/admin/all-users")
    public String findAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "all-users";
    }

    //Создаем нового пользователя
    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", userService.getAllRoles());
        return "user-create";
    }

    @PostMapping("/admin/user-create")
    public String createUser(@ModelAttribute User user, @RequestParam("selectedRoles") List<String> selectedRoles) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : selectedRoles) {
            roles.add(userService.findRoleByName(roleName));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/all-users";
    }

    //Удаляем пользователя
    @GetMapping("/admin/user-delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin/all-users";
    }

    //Обновляем пользователя
    @GetMapping("/admin/user-update")
    public String updateUserForm(@RequestParam("id") int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", userService.getAllRoles());
        return "user-update";
    }

    @PostMapping("/admin/user-update")
    public String updateUser(@ModelAttribute User user, @RequestParam("selectedRoles") List<String> selectedRoles) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : selectedRoles) {
            roles.add(userService.findRoleByName(roleName));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/all-users";
    }

}





