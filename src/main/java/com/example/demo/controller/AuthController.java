package com.example.demo.controller;

import java.util.List;
import com.example.demo.model.User.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            if ("ADMIN".equals(user.getRole())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/user/dashboard";
            }
        }
        model.addAttribute("error", "Invalid credentials");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password, @RequestParam String role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<User> users = userRepository.findAll()
                .stream()
                .filter(u -> "USER".equalsIgnoreCase(u.getRole()))
                .toList();
        model.addAttribute("users", users);
        return "admin_dashboard";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user_dashboard";
    }
    @GetMapping("/home")
public String homePage(Model model) {
    model.addAttribute("today", java.time.LocalDate.now());
    return "home";  // Loads home.html
}

}
