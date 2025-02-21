package io.github.gchape.ebookshop.services.dao;

import io.github.gchape.ebookshop.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ISubjectSqlService extends JpaRepository<Subject, Long> {
    @Query("select s from Subject s where lower(s.subjectName)=lower(:name)")
    Optional<Subject> findByName(String name);
}
