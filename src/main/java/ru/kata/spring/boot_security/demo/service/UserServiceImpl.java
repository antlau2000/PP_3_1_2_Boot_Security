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
        if (user.getUsername() != null) {
            User someUser = repository.findByUsername(user.getUsername());
            if (someUser != null) {
                if (user.getId() != null) {
                    if (!user.getId().equals(someUser.getId())) {
                        throw new RuntimeException("Username not available!");
                    }
                } else {
                    throw new RuntimeException("Username not available!");
                }
            }
        }
//        if (user.getId() != null) {
//            if (user.getUsername() != null) {
//                User someUser = repository.findByUsername(user.getUsername());
//                if (someUser != null) {
//                    if (someUser.getUsername() != null
//                            && someUser.getUsername().equals(user.getUsername())) {
//                        throw new RuntimeException("Username not available!");
//                    }
//                }
//            }
//        }
        if (user.getRoles() == null) {
            Set<Role> userRole = new HashSet<>();
            userRole.add(roleRepository.findByName("USER"));
            user.setRoles(userRole);
        }
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
        return repository.findByUsername(username);
    }
}
