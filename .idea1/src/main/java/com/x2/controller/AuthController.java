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
    @Autowired private UserService userService;

    @GetMapping({"/","/login"})
    public String loginPage(){ return "login"; }

    @GetMapping("/register")
    public String registerPage(){ return "register"; }

    @PostMapping("/doRegister")
    public String doRegister(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             Model model){
        if(userService.findByUsername(username).isPresent() || userService.findByEmail(email).isPresent()){
            model.addAttribute("error","Username or email already exists");
            return "register";
        }
        User u = new User(username,email,password);
        userService.register(u);
        model.addAttribute("msg","Registration successful. Please login.");
        return "login";
    }

    @PostMapping("/doLogin")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,
                          Model model){
        Optional<User> userOpt = userService.login(username, password);
        if(userOpt.isPresent()){
            session.setAttribute("loggedUser", userOpt.get());
            return "redirect:/feed";
        } else {
            model.addAttribute("error","Invalid credentials");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
