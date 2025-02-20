package io.github.gchape.ebookshop.services.api;

import java.util.Optional;

public interface BookAPI {
    void searchByIsbn(String ISBN, Optional<Integer> maxResults);

    void searchBySubject(String subject, Optional<Integer> maxResults);

    void searchByAuthor(String author, Optional<Integer> maxResults);

    void searchByTitle(String title, Optional<Integer> maxResults);
}
