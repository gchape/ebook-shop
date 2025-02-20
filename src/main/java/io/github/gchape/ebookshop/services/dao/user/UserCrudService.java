package io.github.gchape.ebookshop.services.dao.user;

import io.github.gchape.ebookshop.services.dao.IEntityManager;
import io.github.gchape.ebookshop.entities.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserCrudService implements IEntityManager<User> {
    private final EntityManager entityManager;

    @Autowired
    public UserCrudService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }
}
