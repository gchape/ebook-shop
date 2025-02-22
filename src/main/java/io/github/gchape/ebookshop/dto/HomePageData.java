package io.github.gchape.ebookshop.dto;

import io.github.gchape.ebookshop.entities.Book;

import java.util.List;

public record HomePageData(String advertisementBooksSubject, List<Book> advertisementBooks,
                           String requestedBooksSubject,
                           List<Book> requestedBooks) {
}
