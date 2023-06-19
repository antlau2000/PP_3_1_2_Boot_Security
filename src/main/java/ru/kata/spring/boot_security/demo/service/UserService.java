package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {
    void create(User user);
    
    void update(User user);

    User findByUsername(String username);

    void delete(Long id);

    Iterable<User> findAll();
}
