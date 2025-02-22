package io.github.gchape.ebookshop.repositories;

import io.github.gchape.ebookshop.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, String> {
    Subject findBySubjectName(String subjectName);
}
