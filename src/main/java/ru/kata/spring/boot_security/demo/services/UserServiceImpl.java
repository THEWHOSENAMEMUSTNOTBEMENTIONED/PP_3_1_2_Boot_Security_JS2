package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.UserPatchDTO;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public User saveUser(UserPatchDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setSurname(userDTO.getSurname());
        user.setDepartment(userDTO.getDepartment());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        user.setRoles(roleService.mapRoleIdsToRoles(userDTO.getRoleIds()));

        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(int id, UserPatchDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + id + " не найден"));

        if (userDTO.getUsername() != null) existingUser.setUsername(userDTO.getUsername());
        if (userDTO.getSurname() != null) existingUser.setSurname(userDTO.getSurname());
        if (userDTO.getDepartment() != null) existingUser.setDepartment(userDTO.getDepartment());
        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(userDTO.getPassword());
        }

        if (userDTO.getRoleIds() != null) {
            existingUser.setRoles(roleService.mapRoleIdsToRoles(userDTO.getRoleIds()));
        }

        userRepository.save(existingUser);
        return existingUser;
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}