package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;
    @Override
    public void save(Role role) {
        repository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void delete(Role role) {
        repository.delete(role);
    }
}
