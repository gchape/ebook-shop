package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.User;
import io.github.gchape.ebookshop.services.user.UserServiceEMImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final UserServiceEMImpl serviceDao;

    @Autowired
    public SignupServlet(UserServiceEMImpl serviceDao) {
        this.serviceDao = serviceDao;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/signup.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var firstName = req.getParameter("firstname").trim();
        var lastName = req.getParameter("lastname").trim();
        var password = req.getParameter("password").trim();
        var email = req.getParameter("email").trim();

        serviceDao.save(new User(firstName, lastName, email, password));

        resp.sendRedirect("/home");
    }
}
