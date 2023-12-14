package org.linksharing.server.auth;

import jakarta.validation.Valid;
import org.linksharing.server.user.User;
import org.linksharing.server.user.UserProfileDetails;
import org.linksharing.server.user.UserProfileDetailsRepository;
import org.linksharing.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final UserProfileDetailsRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, UserProfileDetailsRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login_page.html";
    }

    @GetMapping("/register")
    public String showRegistrationPage(User user) {
        return "registration_page.html";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result) {

        if (userRepository.existsByUsername(user.getUsername())) {
            result.addError(new FieldError(result.getObjectName(),"username", "Username already in use."));
        }

        if (result.hasErrors()) {
            return "registration_page.html";
        }

        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));

        if (userRepository.existsByEmail(user.getEmail())) {
            return "redirect:/login?emailExists";
        } else if (userRepository.existsByUsername(user.getUsername())) {
            return "redirect:/login?usernameExists";
        }

        UserProfileDetails userProfile = new UserProfileDetails();
        userProfile.setEmail(user.getEmail());

        userRepository.save(user);
        profileRepository.save(userProfile);

        return "redirect:/login";
    }
}