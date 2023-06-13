package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String printUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model, @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        User user = new User();
        List<Role> listRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("errorMessage", errorMessage);
        return "new_user";
    }

    @PostMapping("/new")
    public String submitNewUserForm(@ModelAttribute("user") User user, Model model) {
        try {
            userService.save(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении пользователя: " + e.getMessage());
            return  "redirect:/new?id=" + user.getId() + "&errorMessage=" + e.getMessage();
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUserFrom(Model model, @RequestParam(value = "id") long id, @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        User user = userService.findById(id);
        List<Role> listRoles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("errorMessage", errorMessage);
        return "edit_user";
    }

    @PostMapping("/edit")
    public String submitEditUserForm(@ModelAttribute("user") User user, Model model) {
        try {
            userService.save(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении пользователя: " + e.getMessage());
            return  "redirect:/edit?id=" + user.getId() + "&errorMessage=" + e.getMessage();
        }
        return "redirect:/admin";
    }

    @RequestMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/registration")
    public String registrationUserForm(Model model, @RequestParam(value = "errorMessage", required = false) String errorMessage) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("errorMessage", errorMessage);
        return "registration";
    }

    @PostMapping("/registration")
    public String submitRegistrationUserForm(@ModelAttribute("user") User user, Model model) {
        try {
            userService.save(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Ошибка при сохранении пользователя: " + e.getMessage());
            return  "redirect:/registration?id=" + user.getId() + "&errorMessage=" + e.getMessage();
        }
        return "redirect:/login";
    }
}
