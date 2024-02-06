package org.linksharing.server.http;

import org.linksharing.server.user.User;
import org.linksharing.server.user.UserProfileDetails;
import org.linksharing.server.user.UserProfileDetailsRepository;
import org.linksharing.server.user.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

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
    public ModelAndView personalPublicPage(Principal user) {

        ModelAndView modelAndView = new ModelAndView("public_page");
        UserProfileDetails details = profileRepository.findByEmail(user.getName());
        modelAndView.addObject("details", details);

        return modelAndView;
    }

    @GetMapping("/public/{username}")
    public ModelAndView userPublicPage(@PathVariable(value="username") final String username) {

        ModelAndView modelAndView = new ModelAndView("public_page");
        User user = userRepository.findByUsername(username);
        UserProfileDetails details = profileRepository.findByEmail(user.getEmail());
        modelAndView.addObject("details", details);

        return modelAndView;
    }

}
