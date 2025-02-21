package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.User;

import java.util.Optional;

public interface IUserSqlService {
    Optional<User> queryUserByEmail(String email);
}
