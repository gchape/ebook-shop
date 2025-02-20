package io.github.gchape.ebookshop.services.dao.publisher;

import io.github.gchape.ebookshop.entities.Publisher;
import io.github.gchape.ebookshop.services.dao.IPublisherSqlService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherSqlService implements IPublisherSqlService {
    private final EntityManager entityManager;

    @Autowired
    public PublisherSqlService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Publisher> queryByPublisherName(String publisherName) {
        var query = entityManager.createQuery("select p from Publisher p where lower(p.name)=lower(:publisherName)", Publisher.class);

        return query.setParameter("publisherName", publisherName)
                .getResultStream()
                .findFirst();
    }
}
