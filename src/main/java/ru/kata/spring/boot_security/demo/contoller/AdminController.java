package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String printUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping(value = "/userAdmin")
    public String printUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        return "userAdmin";
    }

    @GetMapping("/new")
    public String newUserForm(@AuthenticationPrincipal UserDetails userDetails, Model model, @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        model.addAttribute("admin", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.findAll());
        model.addAttribute("errorMessage", errorMessage);
        return "new_user";
    }

    @PostMapping("/new")
    public String submitNewUserForm(@ModelAttribute("user") User user, Model model) {
        try {
            userService.create(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении пользователя: " + e.getMessage());
            return  "redirect:/new?id=" + user.getId() + "&errorMessage=" + e.getMessage();
        }
        return "redirect:/admin";
    }

    @PatchMapping("/edit")
    public String submitEditUserForm(@ModelAttribute("user") User user, Model model) {
        try {
            userService.update(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении пользователя: " + e.getMessage());
            return  "redirect:/edit?id=" + user.getId() + "&errorMessage=" + e.getMessage();
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
