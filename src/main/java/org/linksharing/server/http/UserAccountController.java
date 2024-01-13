package org.linksharing.server.http;

import org.linksharing.server.user.UserProfileDetails;
import org.linksharing.server.user.UserProfileDetailsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserAccountController {

    private final UserProfileDetailsRepository userProfileDetailsRepository;

    public UserAccountController(UserProfileDetailsRepository userProfileDetailsRepository) {
        this.userProfileDetailsRepository = userProfileDetailsRepository;
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
    public String userPublicPage(Principal user, Model model) {

        UserProfileDetails details = userProfileDetailsRepository.findByEmail(user.getName());
        model.addAttribute("details", details);

        return "user's_public_page.html";
    }

}
