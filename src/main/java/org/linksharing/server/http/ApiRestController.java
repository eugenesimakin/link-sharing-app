package org.linksharing.server.http;

import org.linksharing.server.links.Link;
import org.linksharing.server.user.UserProfileDetails;
import org.linksharing.server.user.UserProfileDetailsRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

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
    ResponseEntity<List<Link>> getLinks(Principal user) {

        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());

        return new ResponseEntity<>(userProfile.getLinks(), HttpStatus.OK);
    }

    @PostMapping("/links")
    ResponseEntity<List<Link>> updateLinks(Principal user, @RequestBody List<Link> links) {

        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());

        userProfile.setLinks(links);

        profileRepository.save(userProfile);

        return new ResponseEntity<>(links, HttpStatus.OK);
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
    ResponseEntity<UserProfileDetails> updateProfileDetails(Principal user,
                                                            @RequestBody UserProfileDetails profileDetails) {

        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());

        userProfile.setFirstName(profileDetails.getFirstName());
        userProfile.setLastName(profileDetails.getLastName());
        userProfile.setPublicEmail(profileDetails.getPublicEmail());

        profileRepository.save(userProfile);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/profile/picture")
    ResponseEntity<Resource> getProfilePicture(Principal user) {

        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());
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

    @PostMapping("/profile/picture")
    ResponseEntity<UserProfileDetails> updateProfilePicture(Principal user, @RequestParam("file") MultipartFile file) throws IOException {
        UserProfileDetails userProfile = profileRepository.findByEmail(user.getName());

        String[] fileNameParts = file.getOriginalFilename().split("\\.");
        String fileExtension = "." + fileNameParts[fileNameParts.length - 1];

        String rootPath = ResourceUtils.getFile("").getAbsolutePath();
        File picsFolder = new File(rootPath + File.separator + "pics");

        if (!picsFolder.exists()) {
            picsFolder.mkdir();
        }

        Path newFileName = Paths.get(picsFolder.getPath(), userProfile.getEmail() + fileExtension);
        Files.write(newFileName, file.getBytes());

        userProfile.setImageUrl(String.valueOf(newFileName));
        profileRepository.save(userProfile);

        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

}
