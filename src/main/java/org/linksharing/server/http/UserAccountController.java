package org.linksharing.server.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAccountController {
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

}
