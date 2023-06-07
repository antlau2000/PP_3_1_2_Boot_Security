package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    User findById(Long id);

    void delete(Long id);

    List<User> findAll();
}
