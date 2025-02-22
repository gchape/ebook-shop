package io.github.gchape.ebookshop.repositories;

import io.github.gchape.ebookshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
