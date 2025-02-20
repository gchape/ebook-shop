package io.github.gchape.ebookshop.services.transactions;

import io.github.gchape.ebookshop.entities.Author;
import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.entities.Subject;
import io.github.gchape.ebookshop.entities.Publisher;
import io.github.gchape.ebookshop.services.dao.author.AuthorSqlService;
import io.github.gchape.ebookshop.services.dao.category.SubjectSqlService;
import io.github.gchape.ebookshop.services.dao.publisher.PublisherSqlService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final EntityManager entityManager;
    private final AuthorSqlService authorSqlService;
    private final PublisherSqlService publisherSqlService;
    private final SubjectSqlService categorySqlService;

    @Autowired
    public TransactionService(EntityManager entityManager, AuthorSqlService authorSqlService, PublisherSqlService publisherSqlService, SubjectSqlService categorySqlService) {
        this.entityManager = entityManager;
        this.authorSqlService = authorSqlService;
        this.publisherSqlService = publisherSqlService;
        this.categorySqlService = categorySqlService;
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
        Optional<Author> existingAuthor = authorSqlService.queryByAuthorName(author.getName());

        if (existingAuthor.isEmpty()) {
            entityManager.persist(author);
        } else {
            author = existingAuthor.get();
        }
        return author;
    }

    @Transactional
    public Publisher persistPublisher(Publisher publisher) {
        Optional<Publisher> existingPublisher = publisherSqlService.queryByPublisherName(publisher.getName());

        if (existingPublisher.isEmpty()) {
            entityManager.persist(publisher);
        } else {
            publisher = existingPublisher.get();
        }
        return publisher;
    }

    @Transactional
    public Subject persistSubject(Subject subject) {
        Optional<Subject> existingCategory = categorySqlService.queryBySubjectName(subject.getSubjectName());

        if (existingCategory.isEmpty()) {
            entityManager.persist(subject);
        } else {
            subject = existingCategory.get();
        }
        return subject;
    }
}

