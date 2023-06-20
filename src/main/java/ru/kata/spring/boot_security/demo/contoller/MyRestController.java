package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exceptionHandler.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;

    public MyRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Iterable<User> showUsers() {
        return userService.findAll();
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

    @PutMapping("/users")
    public User putUser(@RequestBody User user) {
        userService.update(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "User with ID = " + id + " was deleted";
    }
}
