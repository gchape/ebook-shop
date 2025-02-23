package io.github.gchape.ebookshop.servlets;

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
import java.time.LocalDate;
import java.util.Random;

@WebServlet("/confirmation")
public class ConfirmationServlet extends HttpServlet {
    private final TemplateEngine templateEngine;

    @Autowired
    public ConfirmationServlet(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        synchronized (session) {
            var webContext = new WebContext(JakartaServletWebApplication.buildApplication(getServletContext()).buildExchange(req, resp));

            int orderNumber = Math.abs(new Random().nextInt(9999999));
            LocalDate paymentDate = LocalDate.now();
            webContext.setVariable("orderNumber", orderNumber);
            webContext.setVariable("paymentDate", paymentDate);
            webContext.setVariable("totalPrice", session.getAttribute("totalPrice"));

            resp.setContentType("text/html; charset=utf-8");
            try (var writer = resp.getWriter()) {
                writer.println(templateEngine.process("confirmation.html", webContext));

                session.invalidate();
            }
        }
    }
}
