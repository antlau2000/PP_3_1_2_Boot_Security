package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        if (user.getId() != null) {
            if (user.getUsername() != null) {
                if (repository.findByUsername(user.getUsername()) != null) {
                    throw new RuntimeException("Username not available!");
                }
            }
        }
        Set<Role> userRole = new HashSet<>();
        userRole.add(roleRepository.findByName("USER"));
        user.setRoles(userRole);
        if (user.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        repository.save(user);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("admin")) {
            return testAdmin(username);
        } else if (username.equals("user")) {
            return testUser(username);
        }
        return repository.findByUsername(username);
    }

    private User testAdmin(String username) {
        User admin = repository.findByUsername(username);
        if (admin == null) {
            Set<Role> roles = new HashSet<>();
            Role adminRole = new Role("ADMIN");
            roleRepository.save(adminRole);
            roles.add(roleRepository.findByName("ADMIN"));
            admin = new User(username, "$2a$12$r.MyIcWu4VutAw3z5PcT3OkHnGjO4Xm9AWUllm5d9euY8kQ2TkJ7K", roles);
            repository.save(admin);
            return repository.findByUsername(username);
        } else {
            return admin;
        }
    }

    private User testUser(String username) {
        User user = repository.findByUsername(username);
        if (user == null) {
            Set<Role> roles = new HashSet<>();
            Role userRole = new Role("USER");
            roleRepository.save(userRole);
            roles.add(roleRepository.findByName("USER"));
            user = new User(username, "$2a$12$PG/C8RUuTOcwXjbJXEIcSuTtuVeMnYwRxoKH9SK1w1jWiOGDhIEEi", roles);
            repository.save(user);
            return repository.findByUsername(username);
        } else {
            return user;
        }
    }
}
