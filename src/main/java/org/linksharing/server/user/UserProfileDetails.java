package org.linksharing.server.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "user_profiles")
public class UserProfileDetails {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private int userId;

    @Column(name = "image")
    private String profileImage;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "links")
    private String links;
}
