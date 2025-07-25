package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.model.Book;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.BookRepository;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;
@GetMapping("/login")
public String showLoginPage() {
    return "login"; // must match login.html filename
} 

   @PostMapping("/login")
public String login(@RequestParam String email, @RequestParam String password, Model model) {
    List<User> users = userRepository.findAllByEmailAndPassword(email, password);

    if (!users.isEmpty()) {
        User user = users.get(0); // Get the first matching user

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
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

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user_dashboard";
    }
    @GetMapping("/home")
public String homePage(Model model) {
    model.addAttribute("today", java.time.LocalDate.now());
    return "home";  // Loads home.html
}
@GetMapping("/admin/dashboard")
public String adminDashboard(Model model) {
  List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
    .filter(u -> "USER".equalsIgnoreCase(u.getRole()))
    .toList();

    List<Book> books = bookRepository.findAll();

    model.addAttribute("users", users);
    model.addAttribute("usersCount", users.size());
    model.addAttribute("books", books);
    model.addAttribute("booksCount", books.size());

    return "admin_dashboard";
}
}
