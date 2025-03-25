package org.example.home_internet_hero.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    // This GET endpoint will serve the login.html page when the user navigates to /login
    @GetMapping("/login")
    public String login() {
        return "login"; // This will load login.html from the templates folder
    }

    // This POST endpoint will handle login submission
    @PostMapping("/login")
    public String authentication(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        // Hardcoded credentials for demo
        if ("user@ex1ample.com".equals(email) && "Password1".equals(password)) {
            redirectAttributes.addFlashAttribute("message", "Login successful");
            return "redirect:/";  // Redirect to the home page after successful login
        } else {
            redirectAttributes.addFlashAttribute("message", "Invalid credentials");
            return "redirect:/login";  // Stay on the login page if credentials are incorrect
        }
    }
}
