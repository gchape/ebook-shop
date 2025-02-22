package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.services.PaymentService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private final PaymentService paymentService;

    @Autowired
    public PaymentServlet(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            PaymentService.Order order = paymentService.parseOrder(req.getReader());

            paymentService.save(order);

            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JSON");
        }
    }
}
