package com.example.corporateapp.Controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
//import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//import com.example.corporateapp.Model.AppUser;
import com.example.corporateapp.Service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/login", "/login.html"})
    public String loginPage() {
        return "redirect:/login.html";
    }

    /*@GetMapping({"/register", "/register.html"})
    public String registerPage() {
        return "redirect:/register.html";
    }*/


    @GetMapping("/register")
    public String registerPage() {
    return "forward:/register.html";
}

    @PostMapping("/register")
public String registerSubmit(@RequestParam String name,
                             @RequestParam String email,
                             @RequestParam String password,
                             @RequestParam(required = false) String confirmPassword) {
    if (confirmPassword != null && !password.equals(confirmPassword)) {
        String msg = URLEncoder.encode("Passwords do not match", StandardCharsets.UTF_8);
        return "redirect:/register.html?error=" + msg;
    }

    if (userService.emailExists(email)) {
        String msg = URLEncoder.encode("Email already registered", StandardCharsets.UTF_8);
        return "redirect:/register.html?error=" + msg;
    }

    userService.register(name, email, password);
    String msg = URLEncoder.encode("Registration successful. Please log in.", StandardCharsets.UTF_8);
    return "redirect:/loginpage.html?message=" + msg;
}


   @PostMapping("/login")
public String loginSubmit(@RequestParam String email,
                          @RequestParam String password,
                          HttpServletRequest request) {
    return userService.authenticate(email, password)
            .map(user -> {
                request.getSession().setAttribute("user", user.getEmail());
                return "redirect:/";
            })
            .orElse("redirect:/loginpage.html?error=true");
}



    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login.html";
    }
}
