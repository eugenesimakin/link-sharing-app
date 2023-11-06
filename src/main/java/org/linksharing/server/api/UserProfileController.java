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

    @RequestMapping("/login_page")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login_page.html");
        return modelAndView;
    }

    @RequestMapping("/registration_page")
    public ModelAndView showRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration_page.html");
        return modelAndView;
    }

    @RequestMapping("/profile_details_page")
    public ModelAndView showProfileDetailsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile_details_page.html");
        return modelAndView;
    }

    @PostMapping("/registration_page")
    public String registerUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);

        if (userExists(user)) {
            System.out.println("Email already exists. Please login");
            return "/login_page";
        }

        user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        user.setLinks(new HashMap<>());
        userRepository.save(user);

        return "/registration_success";
    }

    private boolean userExists(User user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }
}
