package io.github.gchape.ebookshop.repositories;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, String> {
    List<Book> findBySubject(Subject subject);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitle(String title);

    Book findByIsbn(String isbn);
}
