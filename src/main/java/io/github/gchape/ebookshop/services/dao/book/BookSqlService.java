package io.github.gchape.ebookshop.services.dao.book;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.services.dao.IBookSqlService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSqlService implements IBookSqlService {
    private final EntityManager entityManager;

    @Autowired
    public BookSqlService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> queryBySubject(String subjectName) {
        var query = entityManager.createQuery(
                "select b from Book b where lower(b.subject.subjectName)=lower(:subjectName)",
                Book.class
        );

        return query
                .setParameter("subjectName", subjectName)
                .getResultList();
    }

    @Override
    public List<Book> queryByTitle(String title) {
        var query = entityManager.createQuery("select b from Book b where b.title like :title", Book.class);

        return query
                .setParameter("title", "%" + title + "%")
                .getResultList();
    }
}
