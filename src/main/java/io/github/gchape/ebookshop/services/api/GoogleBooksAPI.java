package io.github.gchape.ebookshop.services.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.utils.DateUtils;
import io.github.gchape.ebookshop.utils.UniqueUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class GoogleBooksAPI implements BookAPI {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public GoogleBooksAPI(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Book> searchByISBN(String ISBN, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN;
        return fetchBooks(url, maxResults);
    }

    @Override
    public List<Book> searchByGenre(String genre, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:" + genre;
        return fetchBooks(url, maxResults);
    }

    @Override
    public List<Book> searchByAuthor(String author, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=author:" + author;
        return fetchBooks(url, maxResults);
    }

    @Override
    public List<Book> searchByTitle(String title, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title;
        return fetchBooks(url, maxResults);
    }

    @Override
    public List<Book> mapToBookEntity(String json) {
        List<Book> books = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode itemsNode = rootNode.path("items");

            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    Book book = mapBookFromJsonNode(itemNode);
                    books.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    private List<Book> fetchBooks(String url, Optional<Integer> maxResults) {
        String responseJson = restTemplate.getForObject(url + "&maxResults=" + maxResults.orElse(5), String.class);
        return mapToBookEntity(responseJson);
    }

    private Book mapBookFromJsonNode(JsonNode itemNode) {
        JsonNode volumeInfoNode = itemNode.path("volumeInfo");

        Optional<String> isbn = getIsbnFromIdentifiers(volumeInfoNode);
        String title = volumeInfoNode.path("title").asText();
        String publisher = volumeInfoNode.path("publisher").asText();
        LocalDate publishDate = DateUtils.parseLoosely(volumeInfoNode.path("publishedDate").asText());
        String thumbnail = volumeInfoNode.path("imageLinks").path("smallThumbnail").asText();

        Book book = new Book();
        book.setIsbn(isbn.orElse(UniqueUtils.ISBN13()));
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setPublishDate(publishDate);
        book.setThumbnail(thumbnail);

        book.setPrice(UniqueUtils.price());

        return book;
    }

    private Optional<String> getIsbnFromIdentifiers(JsonNode volumeInfoNode) {
        JsonNode industryIdentifiersNode = volumeInfoNode.path("industryIdentifiers");
        if (industryIdentifiersNode.isArray()) {
            for (JsonNode identifierNode : industryIdentifiersNode) {
                if ("ISBN_13".equals(identifierNode.path("type").asText())) {
                    return Optional.ofNullable(identifierNode.path("identifier").asText());
                }
            }
        }
        return Optional.empty();
    }
}
