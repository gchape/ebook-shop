package io.github.gchape.ebookshop.services;

import io.github.gchape.ebookshop.dto.HomePageData;
import io.github.gchape.ebookshop.repositories.IBookRepository;
import io.github.gchape.ebookshop.repositories.ISubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    private final IBookRepository bookRepository;
    private final ISubjectRepository subjectRepository;

    @Autowired
    public HomeService(IBookRepository bookRepository, ISubjectRepository subjectRepository) {
        this.bookRepository = bookRepository;
        this.subjectRepository = subjectRepository;
    }

    public HomePageData getData(String requestedBooksSubject) {
        var advertisementBooks = bookRepository.findByTitle("Spring Boot");

        requestedBooksSubject = requestedBooksSubject == null ? "History" : requestedBooksSubject;
        var subject = subjectRepository.findBySubjectName(requestedBooksSubject);
        var requestedBooks = bookRepository.findBySubject(subject);

        return new HomePageData("Spring Boot", advertisementBooks, requestedBooksSubject, requestedBooks);
    }
}
