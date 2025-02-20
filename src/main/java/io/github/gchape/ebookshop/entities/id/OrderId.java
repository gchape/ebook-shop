package io.github.gchape.ebookshop.entities.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderId implements Serializable {

    @Column(name = "user_name")
    private String username;
    @Column(name = "isbn")
    private String isbn;

    public OrderId() {
    }

    public OrderId(String username, String isbn) {
        this.username = username;
        this.isbn = isbn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
