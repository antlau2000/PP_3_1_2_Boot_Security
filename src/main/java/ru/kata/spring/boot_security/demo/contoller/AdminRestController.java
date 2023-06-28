package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptionHandler.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public Iterable<User> showUsers() {
        return userService.findAll();
    }

    @GetMapping("/roles")
    public Iterable<Role> showRoles() {
        return roleService.findAll();
    }

    @GetMapping("/users/{id}")
    public User showUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID = " + id + " in Database");
        }
        return user;
    }

    @PostMapping("/users")
    public User postUser(@RequestBody User user) {
        userService.create(user);
        return user;
    }

    @PatchMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        userService.update(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "User with ID = " + id + " was deleted";
    }
}
