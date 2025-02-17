package io.github.gchape.ebookshop.services.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gchape.ebookshop.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleBookRestApi implements BookRestApi {
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Autowired
    public GoogleBookRestApi(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Book> fetch(int count) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=programming&maxResults=" + count;
        var json = restTemplate.getForObject(url, String.class);

        return mapJsonToBooks(json);
    }

    @Override
    public List<Book> searchByISBN(String isbn) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        String json = fetch(url);

        return mapJsonToBooks(json);
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=inauthor:" + author;
        String json = fetch(url);

        return mapJsonToBooks(json);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title;
        String json = fetch(url);

        return mapJsonToBooks(json);
    }

    private String fetch(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    public List<Book> mapJsonToBooks(String json) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode itemsNode = rootNode.path("items");
            List<Book> books = new ArrayList<>();

            for (JsonNode itemNode : itemsNode) {
                JsonNode volumeInfo = itemNode.path("volumeInfo");

                Book book = new Book();
                book.setTitle(volumeInfo.path("title").asText());

                JsonNode industryIdentifiers = volumeInfo.path("industryIdentifiers");
                for (JsonNode identifier : industryIdentifiers) {
                    if (identifier.path("type").asText().equals("ISBN_13")) {
                        book.setIsbn(identifier.path("identifier").asText());
                        break;
                    }
                }

                book.setPublishedDate(volumeInfo.path("publishedDate").asText());
                book.setPublisher(volumeInfo.path("publisher").asText());
                book.setSmallThumbnail(volumeInfo.path("imageLinks").path("smallThumbnail").asText());

                books.add(book);
            }

            return books;
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}
