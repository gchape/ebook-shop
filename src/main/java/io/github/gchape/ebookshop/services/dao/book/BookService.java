package io.github.gchape.ebookshop.services.dao.book;

import io.github.gchape.ebookshop.services.dao.IEntityManager;
import io.github.gchape.ebookshop.entities.Book;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IEntityManager<Book> {
    private final EntityManager entityManager;

    @Autowired
    public BookService(EntityManager entityManager, EntityManager entityManager1) {
        this.entityManager = entityManager1;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Book> getAll() {
        return entityManager.createQuery("select b from books b ", Book.class).getResultList();
    }
}
