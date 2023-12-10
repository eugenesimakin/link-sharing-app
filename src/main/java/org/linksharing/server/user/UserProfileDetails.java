package org.linksharing.server.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "user_profiles")
public class UserProfileDetails {

    @Id
    @Column(name = "profile_id", nullable = false, unique = true)
    private int profile_id;

    @Column(name = "image")
    private String profileImage;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "additional_email")
    private String additional_email;

    @Column(name = "links")
    private String links;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "email")
    private User user;
}
