package org.linksharing.server.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.linksharing.server.links.Link;
import org.linksharing.server.links.LinksJsonConverter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity(name = "users")
public class User {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "Email can't be empty")
    @Email(regexp = ".+[@].+[\\.].+",
            message = "Invalid email format. It should have both @ character and .domain\nExample: yourName@emailProvider.domain")
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    @NotEmpty(message = "Username can't be empty")
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password can't be empty")
    private String password;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "deleted_on")
    private Timestamp deletedOn;

    @Column(name = "links")
    @Convert(converter = LinksJsonConverter.class)
    private Map<String, Link> links;

    @PrePersist
    public void setTimeAndLinks() {
        createdOn = new Timestamp(System.currentTimeMillis());
        links = new HashMap<>();
    }

}
