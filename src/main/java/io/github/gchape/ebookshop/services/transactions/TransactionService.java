package io.github.gchape.ebookshop.services.transactions;

import io.github.gchape.ebookshop.entities.Author;
import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.entities.Publisher;
import io.github.gchape.ebookshop.entities.Subject;
import io.github.gchape.ebookshop.services.dao.IAuthorSqlService;
import io.github.gchape.ebookshop.services.dao.IPublisherSqlService;
import io.github.gchape.ebookshop.services.dao.ISubjectSqlService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final EntityManager entityManager;
    private final IAuthorSqlService authorSqlService;
    private final IPublisherSqlService publisherSqlService;
    private final ISubjectSqlService subjectSqlService;

    @Autowired
    public TransactionService(EntityManager entityManager, IAuthorSqlService authorSqlService, IPublisherSqlService publisherSqlService, ISubjectSqlService subjectSqlService) {
        this.entityManager = entityManager;
        this.authorSqlService = authorSqlService;
        this.publisherSqlService = publisherSqlService;
        this.subjectSqlService = subjectSqlService;
    }

    @Transactional
    public void persistBook(Book book) {
        try {
            entityManager.persist(book);
        } catch (EntityExistsException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Author persistAuthor(Author author) {
        Optional<Author> existingAuthor = authorSqlService.findByName(author.getName());

        if (existingAuthor.isEmpty()) {
            entityManager.persist(author);
        } else {
            author = existingAuthor.get();
        }
        return author;
    }

    @Transactional
    public Publisher persistPublisher(Publisher publisher) {
        Optional<Publisher> existingPublisher = publisherSqlService.findByName(publisher.getName());

        if (existingPublisher.isEmpty()) {
            entityManager.persist(publisher);
        } else {
            publisher = existingPublisher.get();
        }
        return publisher;
    }

    @Transactional
    public Subject persistSubject(Subject subject) {
        Optional<Subject> existingCategory = subjectSqlService.findByName(subject.getSubjectName());

        if (existingCategory.isEmpty()) {
            entityManager.persist(subject);
        } else {
            subject = existingCategory.get();
        }
        return subject;
    }
}

