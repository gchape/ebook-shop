package io.github.gchape.ebookshop.services.user;

import io.github.gchape.ebookshop.entities.User;
import io.github.gchape.ebookshop.utils.Unique100k;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService implements UserServiceEMImpl {
    private final EntityManager entityManager;

    @Autowired
    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setUsername(Unique100k.nextString());

        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    @Transactional
    public void update(User user) {
        entityManager.merge(user);
    }
}
