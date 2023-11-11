package org.linksharing.server.api;

import org.linksharing.server.db.user.User;
import org.linksharing.server.db.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home_page.html");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login_page.html");
        return modelAndView;
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password) {

        if (!userRepository.existsByEmail(email)) {
            return "Email not found. Register first";
        } else {
            User user = userRepository.findByEmail(email);
            if (user.getPassword().equals("Encrypted" + password)) {
                return "User validation success.";
            }
            return "Validation failed";
        }
    }

    @GetMapping("/registration")
    public ModelAndView showRegistrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newUser", new User());
        modelAndView.setViewName("registration_page.html");
        return modelAndView;
    }

    @PostMapping("/registerUser")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {

        if (userRepository.existsByEmail(email)) {
            return "/login";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.encryptPassword(password);

        userRepository.save(user);

        return "New user added. Pleas login";
    }

    @GetMapping("/profile_details")
    public ModelAndView showProfileDetailsPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profile_details_page.html");
        return modelAndView;
    }
}