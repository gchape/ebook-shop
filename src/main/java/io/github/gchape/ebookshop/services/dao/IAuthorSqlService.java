package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IAuthorSqlService extends JpaRepository<Author, Long> {
    @Query("select a from Author a where lower(a.name)=lower(:name)")
    Optional<Author> findByName(String name);
}
