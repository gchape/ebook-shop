package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Book;

import java.util.List;

public interface IBookSqlService {
    List<Book> queryBySubject(String subjectName);

    List<Book> queryByTitle(String title);
}
