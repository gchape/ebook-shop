package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.services.HomeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    private final HomeService homeService;

    @Autowired
    public HomeServlet(TemplateEngine templateEngine, HomeService homeService) {
        this.templateEngine = templateEngine;
        this.homeService = homeService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var webContext = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));
        var data = homeService.getData(req.getParameter("subject"));

        webContext.setVariable("advertisementBooksSubject", data.advertisementBooksSubject());
        webContext.setVariable("advertisementBooks", data.advertisementBooks());
        webContext.setVariable("requestedBooksSubject", data.requestedBooksSubject());
        webContext.setVariable("requestedBooks", data.requestedBooks());

        resp.setContentType("text/html; charset=UTF-8");
        try (var writer = resp.getWriter()) {
            writer.println(templateEngine.process("home.html", webContext));
        }
    }
}
