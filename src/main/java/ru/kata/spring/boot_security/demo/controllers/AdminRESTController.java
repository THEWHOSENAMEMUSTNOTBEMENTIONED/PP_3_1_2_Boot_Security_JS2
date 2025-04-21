package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.UserPatchDTO;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminRESTController {

    private final UserService userService;

    @Autowired
    public AdminRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = userService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserPatchDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setSurname(userDTO.getSurname());
        user.setDepartment(userDTO.getDepartment());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoleIds() != null) {
            for (Long roleId : userDTO.getRoleIds()) {
                Role role = userService.getAllRoles().stream()
                        .filter(r -> r.getId() == roleId)
                        .findFirst()
                        .orElse(null);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        user.setRoles(roles);

        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody UserPatchDTO userDTO) {
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (userDTO.getUsername() != null) existingUser.setUsername(userDTO.getUsername());
        if (userDTO.getSurname() != null) existingUser.setSurname(userDTO.getSurname());
        if (userDTO.getDepartment() != null) existingUser.setDepartment(userDTO.getDepartment());
        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(userDTO.getPassword());
        }

        if (userDTO.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>();
            for (Long roleId : userDTO.getRoleIds()) {
                Role role = userService.getAllRoles().stream()
                        .filter(r -> r.getId() == roleId)
                        .findFirst()
                        .orElse(null);
                if (role != null) {
                    roles.add(role);
                }
            }
            existingUser.setRoles(roles);
        }

        userService.saveUser(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}