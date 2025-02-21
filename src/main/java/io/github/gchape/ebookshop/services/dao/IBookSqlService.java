package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookSqlService extends JpaRepository<Book, String> {
    @Query("select b from Book b where lower(b.subject.subjectName)=lower(:subject)")
    List<Book> findBySubject(String subject);

    @Query("select b from Book b where lower(b.title) like lower(CONCAT('%', :title, '%')) ")
    List<Book> findByTitle(String title);
}
