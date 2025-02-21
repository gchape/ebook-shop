package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Author;

import java.util.Optional;

public interface IAuthorSqlService {
    Optional<Author> queryAuthorByName(String authorName);
}
