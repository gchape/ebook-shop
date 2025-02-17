package io.github.gchape.ebookshop.services.book;

import io.github.gchape.ebookshop.entities.Book;

import java.util.List;

public interface BookService {
    void save(Book book);

    void delete(Book book);

    void update(Book book);

    List<Book> getAll();
}
