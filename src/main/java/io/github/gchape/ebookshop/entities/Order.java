package io.github.gchape.ebookshop.entities;

import io.github.gchape.ebookshop.entities.id.OrderId;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Access(value = AccessType.FIELD)
public class Order {
    @EmbeddedId
    private OrderId id;

    @ManyToOne
    @JoinColumn(name = "user_name", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "isbn", insertable = false, updatable = false)
    private Book book;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    public Order() {
    }

    public Order(String username, String isbn, int quantity, LocalDate orderDate) {
        this.id = new OrderId(username, isbn);
        this.quantity = quantity;
        this.orderDate = orderDate;
    }

    public OrderId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
