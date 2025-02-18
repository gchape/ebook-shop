package io.github.gchape.ebookshop.services.api;

import io.github.gchape.ebookshop.entities.Book;

import java.util.List;
import java.util.Optional;

public interface BookAPI {
    List<Book> searchByISBN(String ISBN, Optional<Integer> maxResults);

    List<Book> searchByGenre(String genre, Optional<Integer> maxResults);

    List<Book> searchByAuthor(String author, Optional<Integer> maxResults);

    List<Book> searchByTitle(String title, Optional<Integer> maxResults);

    List<Book> mapToBookEntity(String json);
}
