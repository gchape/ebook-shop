package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IPublisherSqlService extends JpaRepository<Publisher, Long> {
    @Query("select p from Publisher p where lower(p.name)=lower(:name)")
    Optional<Publisher> findByName(String name);
}
