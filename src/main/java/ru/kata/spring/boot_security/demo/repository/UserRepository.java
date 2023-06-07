package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    void deleteById(Long id);
}
