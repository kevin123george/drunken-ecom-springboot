package com.astra.drunken.controllers;

import com.astra.drunken.models.User;
import com.astra.drunken.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @GetMapping("/registration")
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String getUserById(Authentication authentication, Model model) {
        model.addAttribute("userInfo", userService.getUserTo(authentication));
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editUserForm(Authentication authentication, Model model) {
        model.addAttribute("userInfo", userService.getUserTo(authentication));
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editUser(Authentication authentication, @ModelAttribute("user") User user) {
        userService.editUser(user, authentication);
        return "redirect:/user/profile";
    }
}
