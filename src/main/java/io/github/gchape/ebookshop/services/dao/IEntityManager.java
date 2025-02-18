package io.github.gchape.ebookshop.services.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IEntityManager<T> {
    EntityManager getEntityManager();

    @Transactional
    default void save(T t) {
        getEntityManager().persist(t);
    }

    @Transactional
    default void delete(T t) {
        getEntityManager().remove(t);
    }

    List<T> getAll();

    @Transactional
    default void update(T t) {
        getEntityManager().merge(t);
    }
}
