package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();
    Role findRoleByName(String roleName);
    Set<Role> mapRoleIdsToRoles(Set<Long> roleIds);
    Set<Role> mapRoleNamesToRoles(List<String> roleNames); // Новый метод
}