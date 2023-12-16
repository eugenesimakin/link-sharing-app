package org.linksharing.server.http;

import org.linksharing.server.user.UserProfileDetails;
import org.linksharing.server.user.UserProfileDetailsRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;

import static org.springframework.http.MediaType.IMAGE_JPEG;

@RestController
@RequestMapping("/api")
public class ApiRestController {

    private final UserProfileDetailsRepository profileRepository;

    public ApiRestController(UserProfileDetailsRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

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
    ResponseEntity<UserProfileDetails> getProfileDetails(Principal user) {

        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());

        if (userProfile != null) {
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        }
      
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/profile")
    ResponseEntity<UserProfileDetails> updateProfileDetails(Principal user, @RequestBody UserProfileDetails profileDetails) {

        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());

        userProfile.setFirstName(profileDetails.getFirstName());
        userProfile.setLastName(profileDetails.getLastName());
        userProfile.setPublicEmail(profileDetails.getPublicEmail());

        profileRepository.save(userProfile);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
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
