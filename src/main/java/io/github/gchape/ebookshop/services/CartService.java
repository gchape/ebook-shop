package io.github.gchape.ebookshop.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.repositories.IBookRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {
    private final ObjectMapper objectMapper;
    private final IBookRepository bookRepository;

    private final List<String> booksIsbn = new ArrayList<>();

    @Autowired
    public CartService(ObjectMapper objectMapper, IBookRepository bookRepository) {
        this.objectMapper = objectMapper;
        this.bookRepository = bookRepository;
    }

    public void add(HttpServletRequest request) {
        var requestBody = readRequestBody(request);
        var isbn = requestBody.get("isbn");

        booksIsbn.add(isbn);
    }

    public Map<Book, Long> groupByIsbn() {
        return booksIsbn
                .stream()
                .map(bookRepository::findByIsbn)
                .collect(Collectors.groupingBy(book -> book, Collectors.counting()));
    }

    public BigDecimal getTotalPrice() {
        return booksIsbn
                .stream()
                .map(bookRepository::findByIsbn)
                .map(Book::getPrice).map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void remove(HttpServletRequest request) {
        var requestBody = readRequestBody(request);
        var isbn = requestBody.get("isbn");

        booksIsbn.removeIf(existingIsbn -> existingIsbn.equals(isbn));
    }

    public void update(HttpServletRequest request) {
        Map<String, String> requestData = readRequestBody(request);
        var isbn = requestData.get("isbn");
        var action = requestData.get("action");

        switch (action) {
            case "increase":
                booksIsbn.add(isbn);
                break;
            case "decrease":
                booksIsbn.remove(isbn);
                break;
        }
    }

    private Map<String, String> readRequestBody(HttpServletRequest req) {
        try (BufferedReader reader = req.getReader()) {
            var json = new StringBuilder();

            for (String line; (line = reader.readLine()) != null; ) {
                json.append(line);
            }

            return objectMapper.readValue(json.toString(), new TypeReference<>() {
            });
        } catch (IOException exception) {
            throw new RuntimeException("Failed to parse request body", exception);
        }
    }

    public String isbnAndQuantity(Map<Book, Long> bookAndQuantity) throws JsonProcessingException {
        if (bookAndQuantity == null) {
            throw new IllegalArgumentException("Book and quantity cannot be null");
        }

        return objectMapper.writeValueAsString(bookAndQuantity.entrySet()
                .stream()
                .map(s -> Map.entry(s.getKey().getIsbn(), s.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }
}
