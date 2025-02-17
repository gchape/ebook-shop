package io.github.gchape.ebookshop.services.book;

import io.github.gchape.ebookshop.entities.Book;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceJdbcImpl implements BookService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookServiceJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void save(Book book) {
        jdbcTemplate.update("insert into books(isbn, title, published_date, publisher, small_thumbnail) values (?, ?, ?, ?, ?)",
                book.getIsbn(), book.getTitle(), book.getPublishedDate(), book.getPublisher(), book.getSmallThumbnail());
    }

    @Transactional
    @Override
    public void delete(Book book) {
        jdbcTemplate.update("DELETE FROM books WHERE isbn = ?", book.getIsbn());
    }

    @Transactional
    @Override
    public void update(Book book) {
        jdbcTemplate.update("UPDATE books SET title = ?, published_date = ?, publisher = ?, small_thumbnail = ? WHERE isbn = ?",
                book.getTitle(), book.getPublishedDate(), book.getPublisher(), book.getSmallThumbnail(), book.getIsbn());
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM books",
                (rs, rowNum) -> new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("published_date"),
                        rs.getString("publisher"),
                        rs.getString("small_thumbnail")
                ));
    }
}
