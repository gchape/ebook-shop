package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.Book;
import io.github.gchape.ebookshop.services.CartService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CartService cartService;
    private final TemplateEngine templateEngine;

    @Autowired
    public CartServlet(CartService cartService, TemplateEngine templateEngine) {
        this.cartService = cartService;
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            session = req.getSession(true);
        }

        synchronized (session) {
            var webContext = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));

            Map<Book, Long> bookAndQuantity = cartService.groupByIsbn();

            session.setAttribute("bookAndQuantity", bookAndQuantity);
            session.setAttribute("isbnAndQuantity", cartService.isbnAndQuantity(bookAndQuantity));
            session.setAttribute("totalPrice", String.format("%.2f", cartService.getTotalPrice()));

            resp.setContentType("text/html; charset=UTF-8");
            try (var writer = resp.getWriter()) {
                writer.println(templateEngine.process("cart.html", webContext));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        cartService.add(req);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        cartService.update(req);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        cartService.remove(req);
    }
}
