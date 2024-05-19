package com.flipr.mongo_db_admin_panel.controller;

import com.flipr.mongo_db_admin_panel.modles.User;
import com.flipr.mongo_db_admin_panel.repositories.UserRepository;
import com.flipr.mongo_db_admin_panel.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepo;

    @ModelAttribute
    public User getUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            User user = userRepo.findByUserName(email);
            if (user != null) {
                m.addAttribute("user", user);
                return user;
            }
        }
        // Handle the case where the user or principal is null
        return null;
    }

    @GetMapping("/user")
    public String HomeAfterLogin() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/user/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           HttpSession session, HttpServletRequest req) {
        String url = req.getRequestURL().toString();
        url = url.replace(req.getServletPath(), "");

        // Create a new User object
        User user = new User();
        user.setUserName(email);
        user.setPassword(password);

        // Save the user using userService.saveUser
        User u = userService.saveUser(user, url);

        if (u != null) {
            session.setAttribute("msg", "User Created Successfully,Verify Your Mail!!");
            return "register";
        } else {
            session.setAttribute("msg", "Failed to create user,can be already exist");
            return "register";
        }
    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code, org.springframework.ui.Model m) {
        if (userService.verifyAccount(code)) {
            m.addAttribute("msg", "account verified successfully!");
        } else
            m.addAttribute("msg", "account verification failed!");
        return "message";
    }


}
