package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserSqlService extends JpaRepository<User, String> {
    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);
}
