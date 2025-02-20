package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Subject;

import java.util.Optional;

public interface ISubjectSqlService {
    Optional<Subject> queryBySubjectName(String subjectName);
}
