package org.linksharing.server.api;

import org.linksharing.server.db.user.User;
import org.linksharing.server.db.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.HashMap;

@RestController
public class UserProfileController {

    UserRepository userRepository;

    @Autowired
    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/check")
    ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @RequestMapping("/")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home_page.html");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login_page.html");
        return modelAndView;
    }

    @RequestMapping("/registration")
    public ModelAndView showRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration_page.html");
        return modelAndView;
    }

    @RequestMapping("/profile_details")
    public ModelAndView showProfileDetailsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile_details_page.html");
        return modelAndView;
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);

        if (userRepository.existsByEmail(user.getEmail())) {
            return "/login_page";
        }

        userRepository.save(user);

        return "/registration_success";
    }
}