package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class InitAdminConfig {
    private final RoleService roleService;
    private final UserService userService;

    public InitAdminConfig(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role("ADMIN");
        roleService.save(adminRole);
        Role userRole = new Role("USER");
        roleService.save(userRole);
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        roles.add(adminRole);
        User admin = new User("admin", "admin", "name", "surname", 15, roles);
        userService.create(admin);
        roles.remove(adminRole);
        User user = new User("user", "123", "name", "surname", 15, roles);
        userService.create(user);
    }
}
