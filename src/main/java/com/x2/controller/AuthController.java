package com.x2.controller;

import java.util.Optional;
import com.x2.model.User;
import com.x2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    // Use constructor injection for best practice
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/login"})
    public String loginPage(){ return "login"; }

    @GetMapping("/register")
    public String registerPage(){ return "register"; }

    @PostMapping("/doRegister")
    public String doRegister(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             Model model){
        // Basic server-side validation
        if(userService.findByUsername(username).isPresent()){
            model.addAttribute("error","Username already taken.");
            return "register";
        }
        if(userService.findByEmail(email).isPresent()){
            model.addAttribute("error","Email already registered.");
            return "register";
        }

        User u = new User(username, email, password);
        userService.register(u);

        model.addAttribute("msg", "Registration successful. Please login.");
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model){

        Optional<User> userOpt = userService.login(username, password);

        if(userOpt.isPresent()){
            // Login successful, set user in session
            session.setAttribute("loggedUser", userOpt.get());
            // Redirect to the main feed page
            return "redirect:/posts/feed";
        } else {
            // Login failed
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate(); // Invalidate the session
        return "redirect:/login"; // Redirect to the login page
    }
}