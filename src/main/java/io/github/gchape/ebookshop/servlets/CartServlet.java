package io.github.gchape.ebookshop.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CartService cartService;
    private final TemplateEngine templateEngine;
    private final ObjectMapper objectMapper;

    @Autowired
    public CartServlet(CartService cartService, TemplateEngine templateEngine, ObjectMapper objectMapper) {
        this.cartService = cartService;
        this.objectMapper = objectMapper;
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

            Map<Book, Long> bookQuantityMap = cartService.getBookQuantityMap();
            BigDecimal totalPrice = cartService.getTotalPrice();

            session.setAttribute("bookQuantityMap", bookQuantityMap);

            session.setAttribute("booksIsbnQuantity", objectMapper.writeValueAsString(bookQuantityMap.entrySet()
                    .stream()
                    .map(s -> Map.entry(s.getKey().getIsbn(), s.getValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));

            session.setAttribute("totalPrice", String.format("%.2f", totalPrice));

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
        cartService.removeAll(req);
    }
}
