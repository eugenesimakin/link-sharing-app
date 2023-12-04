package org.linksharing.server.auth;

import jakarta.validation.Valid;
import org.linksharing.server.user.User;
import org.linksharing.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
    public String registerUser(@Valid User user, BindingResult result,
                               @RequestParam Map<String, String> incomingParams) {

        boolean formHasErrors = false;

        String[] allParams = new String[3];
        allParams[0] = incomingParams.get("username");
        allParams[1] = incomingParams.get("email");
        allParams[2] = incomingParams.get("password");

        StringBuilder urlTemplate = new StringBuilder("redirect:/register?");

        for (int i = 0; i < allParams.length; i++) {
            if (!allParams[i].isBlank() && i < allParams.length - 1) {
                int index = i;
                urlTemplate.append(incomingParams.entrySet().stream().
                        filter(c -> c.getValue().equals(allParams[index])).
                        toList().get(0).
                        getKey()).append("=").append(allParams[i]).append("&");
            } else if (!allParams[i].isBlank() && i == allParams.length - 1) {
                urlTemplate.append("passwordIsOk");
            } else if (allParams[i].isBlank() && i == allParams.length - 1) {
                urlTemplate.append("passwordIsNotOk");
            } else {
                formHasErrors = true;
                urlTemplate.append("hasErrors&");
            }
        }

        if (formHasErrors) {
            return urlTemplate.toString();
        }

        String pass = user.getPassword();
        user.setPassword(passwordEncoder.encode(pass));

        if (userRepository.existsByEmail(user.getEmail())) {
            return "redirect:/login?userExists";
        }

        userRepository.save(user);

        return "redirect:/login";
    }
}