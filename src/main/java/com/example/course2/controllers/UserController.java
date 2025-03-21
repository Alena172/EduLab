package com.example.course2.controllers;

import com.example.course2.services.UserService;
import com.example.course2.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling user-related requests.
 */
@Controller
@RequestMapping()
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Display the login page.
     *
     * @param model the model
     * @return the login page
     */
    @GetMapping("/login")
    public String getRegistration(Model model) {
        model.addAttribute("userReg", new User());
        return "form";
    }

    /**
     * Handle user registration.
     *
     * @param user the user to register
     * @param model the model
     * @return the redirect URL
     */
    @PostMapping("reg")
    public String addNewUser(@ModelAttribute("userReg") User user, Model model) {
        userService.addUser(user);
        return "redirect:/login";
    }
}
