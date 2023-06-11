package ru.kata.spring.boot_security.demo.contoller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String printUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Long id = ((User) userDetails).getId();
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin")
    public String printUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/new")
    public String submitNewUserForm(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editUserFrom(Model model, @RequestParam(value = "id") long id) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/edit")
    public String submitEditUserForm(@ModelAttribute("user") User user, Authentication authentication) {
        userService.save(user);
        if (authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/admin";
        } else {
            return "redirect:/user";
        }
    }

    @RequestMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
