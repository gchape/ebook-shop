package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.services.api.BookAPI;
import io.github.gchape.ebookshop.services.book.BookService;
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
import java.util.List;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    private List<Book> books;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private BookAPI bookAPI;
    @Autowired
    private BookService bookService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

//        this.books = bookAPI.fetch(5);
//        this.books.forEach(bookService::save);
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
