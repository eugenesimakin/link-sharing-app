package org.linksharing.server.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email", nullable = false, unique = true)
    @NotEmpty(message = "Email can't be empty")
    @Email(regexp = ".+[@].+[\\.].+",
            message = "Invalid email format. It should have both @ character and domain.\n"
                    + "Example: yourName@emailProvider.domain")
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

    @PrePersist
    public void prePersist() {
        createdOn = new Timestamp(System.currentTimeMillis());
    }

}
