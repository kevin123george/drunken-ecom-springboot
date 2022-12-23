package com.astra.drunken.controllers;

import com.astra.drunken.models.Address;
import com.astra.drunken.models.User;
import com.astra.drunken.services.OrderService;
import com.astra.drunken.services.TemplateHelper;
import com.astra.drunken.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;
    private final TemplateHelper templateHelper;

    @Autowired
    public UserController(UserService userService, OrderService orderService, TemplateHelper templateHelper) {
        super();
        this.userService = userService;
        this.orderService = orderService;
        this.templateHelper = templateHelper;
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
    public String registerUserAccount(@Valid @ModelAttribute("user") User user,BindingResult error) {
        if (error.hasErrors()){
//            model.addAttribute("crate", );
            return "registration";
        }
        userService.save(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String getUserById(Authentication authentication, Model model) {
        var order = orderService.getOrderByUserAndNotActive(authentication);
        model.addAttribute("userInfo", userService.getUserTo(authentication));
        model.addAttribute("order", order);
        templateHelper.defaultTemplateModel(model, authentication);
        return "profile";
    }

    @GetMapping("/profile/add/address")
    public String addAddressForm(Authentication authentication, Model model) {
        model.addAttribute("address", userService.getUserAddressTo(authentication));
        templateHelper.defaultTemplateModel(model, authentication);
        return "edit-address";
    }

    @PostMapping("/profile/add/address")
    public String addAddress(Authentication authentication, @Valid @ModelAttribute("address") Address address, BindingResult error, Model model) {
        if (error.hasErrors()){
//            model.addAttribute("crate", );
            return "edit-address";
        }
        userService.editAddress(address, authentication);
        templateHelper.defaultTemplateModel(model, authentication);
        return "redirect:/user/profile";
    }

    @GetMapping("/profile/edit")
    public String editUserForm(Authentication authentication, Model model) {
        model.addAttribute("userInfo", userService.getUserTo(authentication));
        templateHelper.defaultTemplateModel(model, authentication);
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editUser(Authentication authentication, @ModelAttribute("user") User user, Model model) {
        userService.editUser(user, authentication);
        templateHelper.defaultTemplateModel(model, authentication);
        return "redirect:/user/profile";
    }
}
