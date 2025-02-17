package io.github.gchape.ebookshop.services.api;

import io.github.gchape.ebookshop.entities.Book;

import java.util.List;

public interface BookAPI {
    List<Book> fetch(int count);

    List<Book> searchByISBN(String ISBN);

    List<Book> searchByAuthor(String author);

    List<Book> searchByTitle(String title);
}
