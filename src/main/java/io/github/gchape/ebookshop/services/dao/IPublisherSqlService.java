package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Publisher;

import java.util.Optional;

public interface IPublisherSqlService {
    Optional<Publisher> queryByPublisherName(String publisherName);
}
