package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.services.api.BookRestApi;
import io.github.gchape.ebookshop.services.dao.IEntityManager;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    private List<Book> books;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private BookRestApi bookAPI;
    @Autowired
    private IEntityManager<Book> bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            var json = Files.readString(Path.of(getClass().getResource("/book-data.txt").toURI()));

            var books = bookAPI.mapJsonToBooks(json);
            books.forEach(bookService::save);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var ctx = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));
        ctx.setVariable("books", books);

        var home = templateEngine.process("home.html", ctx);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            writer.println(home);
        }
    }
}
