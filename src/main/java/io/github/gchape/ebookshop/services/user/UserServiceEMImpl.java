package io.github.gchape.ebookshop.services.user;

import io.github.gchape.ebookshop.entities.User;

import java.util.List;

public interface UserServiceEMImpl {
    void save(User t);

    void delete(User t);

    List<User> getAll();

    void update(User t);
}
