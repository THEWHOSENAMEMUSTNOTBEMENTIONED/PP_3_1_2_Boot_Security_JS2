package ru.kata.spring.boot_security.demo.Services;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> findAll();
    User findById(int id);
    void saveUser(User user);
    void deleteById(int id);
    Set<Role> getAllRoles();
    Role findRoleByName(String roleName);
}
