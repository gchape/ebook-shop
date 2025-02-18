package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.services.api.BookAPI;
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
import java.util.Optional;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private BookAPI bookAPI;

    private List<Book> genreBooks;
    private List<Book> springBootBooks;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        genreBooks = bookAPI.searchByGenre("history", Optional.empty());
        springBootBooks = bookAPI.searchByTitle("spring boot", Optional.empty());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var ctx = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));

        if (req.getParameter("genre") != null) {
            genreBooks = bookAPI.searchByGenre(req.getParameter("genre").toLowerCase(), Optional.empty());
        }

        ctx.setVariable("genreBooks", genreBooks);
        ctx.setVariable("springBootBooks", springBootBooks);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            writer.println(templateEngine.process("home.html", ctx));
        }
    }
}
