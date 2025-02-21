package io.github.gchape.ebookshop.services.dao.user;

import io.github.gchape.ebookshop.entities.User;
import io.github.gchape.ebookshop.services.dao.IUserSqlService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSqlService implements IUserSqlService {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<User> queryUserByEmail(String email) {
        var query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);

        return query.setParameter("email", email).getResultList()
                .stream()
                .findFirst();
    }
}
