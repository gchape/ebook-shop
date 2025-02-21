package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.services.dao.book.BookSqlService;
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
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private BookSqlService bookSqlService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var ctx = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));

        List<Book> advertisementBooks = bookSqlService.queryBookByTitle("Spring Boot");
        ctx.setVariable("advertisementBooksSubject", "Spring Boot");
        ctx.setVariable("advertisementBooks", advertisementBooks);

        String subject = req.getParameter("subject") == null ? "History" : req.getParameter("subject");
        List<Book> query = bookSqlService.queryBookBySubject(subject);
        ctx.setVariable("queryBooksSubject", subject);
        ctx.setVariable("queryBooks", query);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            writer.println(templateEngine.process("home.html", ctx));
        }
    }
}
