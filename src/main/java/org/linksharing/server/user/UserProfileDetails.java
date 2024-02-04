package org.linksharing.server.user;

import jakarta.persistence.*;
import lombok.Data;
import org.linksharing.server.links.Link;
import org.linksharing.server.links.LinksJsonConverter;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_profile_details")
public class UserProfileDetails {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "public_email")
    private String publicEmail;

    @Column(name = "links")
    @Convert(converter = LinksJsonConverter.class)
    private List<Link> links = new ArrayList<>();

}
