package io.github.gchape.ebookshop.services.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.gchape.ebookshop.entities.Author;
import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.entities.Publisher;
import io.github.gchape.ebookshop.entities.Subject;
import io.github.gchape.ebookshop.services.transactions.TransactionService;
import io.github.gchape.ebookshop.utils.DateUtils;
import io.github.gchape.ebookshop.utils.UniqueUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class GoogleBooksAPI implements BookAPI {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final TransactionService bookTransactionService;
    private final TransactionService transactionService;
    private String subject;

    public GoogleBooksAPI(ObjectMapper objectMapper, RestTemplate restTemplate, TransactionService bookTransactionService, TransactionService transactionService) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.bookTransactionService = bookTransactionService;
        this.transactionService = transactionService;
    }

    private Book createBook(Optional<String> isbn, String title, LocalDate publishDate, String thumbnail) {
        Book book = new Book();
        book.setIsbn(isbn.orElse(UniqueUtils.ISBN13()));
        book.setTitle(title);
        book.setPublishDate(publishDate);
        book.setThumbnail(thumbnail);

        book.setPrice(UniqueUtils.price());
        return book;
    }

    @Override
    public void searchByIsbn(String isbn, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;

        readJsonAndPersist(url, maxResults);
    }

    @Override
    public void searchBySubject(String subject, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=subject:" + subject;

        this.subject = subject;

        readJsonAndPersist(url, maxResults);

        this.subject = null;
    }

    @Override
    public void searchByAuthor(String author, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=author:" + author;

        readJsonAndPersist(url, maxResults);
    }

    @Override
    public void searchByTitle(String title, Optional<Integer> maxResults) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=intitle:" + title;

        readJsonAndPersist(url, maxResults);
    }

    private void readJsonAndPersist(String url, Optional<Integer> maxResults) {
        String json = restTemplate.getForObject(url + "&maxResults=" + maxResults.orElse(5), String.class);

        System.out.println(json);
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode itemsNode = rootNode.path("items");

            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    JsonNode volumeInfoNode = itemNode.path("volumeInfo");
                    JsonNode authorsNode = volumeInfoNode.path("authors");

                    Optional<String> isbn = getIsbnFromIdentifiers(volumeInfoNode);
                    String title = volumeInfoNode.path("title").asText();
                    String publisherName = volumeInfoNode.path("publisher").asText();
                    LocalDate publishDate = DateUtils.parseLoosely(volumeInfoNode.path("publishedDate").asText());
                    String thumbnail = volumeInfoNode.path("imageLinks").path("smallThumbnail").asText();

                    Book book = createBook(isbn, title, publishDate, thumbnail);

                    if (authorsNode.isArray()) {
                        for (JsonNode authorNode : authorsNode) {
                            var author = bookTransactionService.persistAuthor(new Author(authorNode.asText()));
                            book.getAuthors().add(author);
                        }
                    }

                    var publisher = bookTransactionService.persistPublisher(new Publisher(publisherName));

                    book.setPublisher(publisher);

                    if (this.subject != null) {
                        var subject = transactionService.persistSubject(new Subject(this.subject));
                        book.setSubject(subject);
                    }

                    bookTransactionService.persistBook(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
