package io.github.gchape.ebookshop.services.dao.category;

import io.github.gchape.ebookshop.entities.Subject;
import io.github.gchape.ebookshop.services.dao.ISubjectSqlService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectSqlService implements ISubjectSqlService {
    private final EntityManager entityManager;

    @Autowired
    public SubjectSqlService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Subject> querySubjectByName(String subjectName) {
        var query = entityManager.createQuery("select s from Subject s where lower(s.subjectName)=lower(:subjectName)", Subject.class);

        return query.setParameter("subjectName", subjectName)
                .getResultStream()
                .findFirst();
    }
}
