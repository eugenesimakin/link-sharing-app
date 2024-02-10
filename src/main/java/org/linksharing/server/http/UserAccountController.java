package org.linksharing.server.http;

import org.linksharing.server.user.User;
import org.linksharing.server.user.UserProfileDetails;
import org.linksharing.server.user.UserProfileDetailsRepository;
import org.linksharing.server.user.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@Controller
public class UserAccountController {

    private final UserProfileDetailsRepository profileRepository;
    private final UserRepository userRepository;

    public UserAccountController(UserProfileDetailsRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String homePage() {
        return "redirect:/links";
    }

    @GetMapping("/links")
    public String profileLinksPage() {
        return "profile_links_page.html";
    }

    @GetMapping("/profile")
    public String profileDetailsPage() {
        return "profile_details_page.html";
    }

    @GetMapping("/public")
    public ModelAndView personalPublicPage(Principal principalUser) {

        UserProfileDetails details = profileRepository.findByEmail(principalUser.getName());
        User user = userRepository.findByEmail(principalUser.getName());

        ModelAndView modelAndView = new ModelAndView("public_page");
        modelAndView.addObject("details", details);
        modelAndView.addObject("username", user.getUsername());

        return modelAndView;
    }

    @GetMapping("/public/{username}")
    public ModelAndView userPublicPage(@PathVariable(value="username") final String username) {

        ModelAndView modelAndView = new ModelAndView("public_page");
        User user = userRepository.findByUsername(username);
        UserProfileDetails details = profileRepository.findByEmail(user.getEmail());

        modelAndView.addObject("details", details);
        modelAndView.addObject("username", user.getUsername());

        return modelAndView;
    }

    @GetMapping("/public/picture/{email}")
    ResponseEntity<Resource> getProfilePicture(@PathVariable(value="email") final String email) {

        UserProfileDetails userProfile = profileRepository.findByEmail(email);
        Resource imgResource;

        if (userProfile.getImageUrl() != null) {
            imgResource = new PathResource(userProfile.getImageUrl());
        } else {
            imgResource = new ClassPathResource("static/placeholder.jpg");
        }

        return ResponseEntity
                .ok()
                .contentType(IMAGE_JPEG)
                .body(imgResource);
    }

}
