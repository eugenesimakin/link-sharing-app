package org.linksharing.server.db.user;

import jakarta.persistence.*;
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
    @Column(name = "email")
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
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
