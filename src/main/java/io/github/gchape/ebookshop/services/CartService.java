package io.github.gchape.ebookshop.services;

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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> booksIsbn = new ArrayList<>();

    private final IBookRepository bookRepository;

    @Autowired
    public CartService(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void add(HttpServletRequest req) {
        var isbn = extractValueFromRequest(req, "isbn");
        booksIsbn.add(isbn);
    }

    public String extractValueFromRequest(HttpServletRequest req, String key) {
        Map<String, String> requestData = readRequestBody(req);
        return requestData.get(key);
    }

    public Map<Book, Long> getBookOccurenceMap() {
        return booksIsbn.stream().map(bookRepository::findByIsbn).collect(Collectors.groupingBy(book -> book, Collectors.counting()));
    }

    public BigDecimal getTotalPrice() {
        return booksIsbn.stream().map(bookRepository::findByIsbn).map(Book::getPrice).map(BigDecimal::valueOf).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void removeAll(HttpServletRequest request) {
        var isbn = extractValueFromRequest(request, "isbn");
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
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString();
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading request body", e);
        }
    }
}
