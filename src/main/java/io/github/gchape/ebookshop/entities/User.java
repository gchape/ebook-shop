package io.github.gchape.ebookshop.entities;

import io.github.gchape.ebookshop.utils.UniqueUtils;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
@Access(value = AccessType.FIELD)
public class User {

    @Id
    @Column(name = "user_name")
    private String username;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    public User(String name, String email, String password) {
        this.username = UniqueUtils.username();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    protected User() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
