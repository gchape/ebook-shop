package io.github.gchape.ebookshop.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity(name = "books")
public class Book {
    @Id
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;
    @Column(name = "publisher", nullable = false)
    private String publisher;
    @Column(name = "price")
    private double price;
    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    public Book() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishedDate) {
        this.publishDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String smallThumbnail) {
        this.thumbnail = smallThumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", publisher='" + publisher + '\'' +
                ", price=" + price +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
