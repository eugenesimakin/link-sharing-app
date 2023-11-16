package org.linksharing.server.user;

import lombok.Data;

@Data
public class UserProfileDetails {
    private String profilePicture;
    private String firstName;
    private String lastName;
    private String email;
}
