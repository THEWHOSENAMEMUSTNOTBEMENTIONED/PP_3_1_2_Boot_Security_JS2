package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.UserPatchDTO;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(int id);
    User saveUser(UserPatchDTO userDTO); // Новый метод для создания
    User updateUser(int id, UserPatchDTO userDTO); // Новый метод для обновления
    void deleteById(int id);
}