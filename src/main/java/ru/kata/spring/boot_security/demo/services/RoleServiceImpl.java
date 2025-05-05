package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles == null) {
            return new ArrayList<>();
        }
        return roles;
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Set<Role> mapRoleIdsToRoles(Set<Long> roleIds) {
        if (roleIds == null) {
            return new HashSet<>();
        }
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId.intValue())
                    .orElseThrow(() -> new IllegalArgumentException("Роль с ID " + roleId + " не найдена"));
            roles.add(role);
        }
        return roles;
    }

    @Override
    public Set<Role> mapRoleNamesToRoles(List<String> roleNames) {
        if (roleNames == null) {
            return new HashSet<>();
        }
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = findRoleByName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }
}