package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Book;

import java.util.List;

public interface IBookSqlService {
    List<Book> queryBookBySubject(String subjectName);

    List<Book> queryBookByTitle(String title);
}
