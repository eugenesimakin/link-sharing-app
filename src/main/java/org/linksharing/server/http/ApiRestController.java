package org.linksharing.server.http;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    @GetMapping("/check")
    ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @GetMapping("/links")
    ResponseEntity<?> getLinks(Principal user) {
        return null;
    }

    @PostMapping("/links")
    ResponseEntity<?> updateLinks(Principal user) {
        return null;
    }

    @GetMapping("/profile")
    ResponseEntity<?> getProfileDetails(Principal user) {
        return null;
    }

    @PostMapping("/profile")
    ResponseEntity<?> updateProfileDetails(Principal user) {
        return null;
    }

    @GetMapping("/profile/picture")
    ResponseEntity<?> getProfilePicture(Principal user) throws FileNotFoundException {
        File img = new File("../image.jpg");
        return ResponseEntity
                .ok()
                .contentType(IMAGE_JPEG)
                .body(new InputStreamResource(new FileInputStream(img)));
    }

    @PostMapping("/profile/picture")
    ResponseEntity<?> updateProfilePicture(Principal user) {
        return null;
    }

}
