package io.github.gchape.ebookshop.services.dao.author;

import io.github.gchape.ebookshop.entities.Author;
import io.github.gchape.ebookshop.services.dao.IAuthorSqlService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorSqlService implements IAuthorSqlService {
    private final EntityManager entityManager;

    @Autowired
    public AuthorSqlService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Author> queryByAuthorName(String authorName) {
        var query = entityManager.createQuery("select a from Author a where lower(a.name)=lower(:authorName)", Author.class);

        return query.setParameter("authorName", authorName)
                .getResultStream()
                .findFirst();
    }
}
