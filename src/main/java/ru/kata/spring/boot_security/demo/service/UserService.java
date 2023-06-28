package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void create(User user);
    
    void update(User user);

    User findById(Long id);

    User findByUsername(String username);

    void delete(Long id);

    List<User> findAll();
}
