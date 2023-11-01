package org.linksharing.server.api;

import org.linksharing.server.db.user.User;
import org.linksharing.server.db.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.util.HashMap;

@Controller
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

    @GetMapping("/")
    public String showHomePage() {
        return "home_page";
    }

    @GetMapping("/login_page")
    public String showLoginPage() {
        return "login_page";
    }

    @GetMapping("/registration_page")
    public String showRegistrationPage() {
        return "registration_page";
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
