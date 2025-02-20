package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.User;
import io.github.gchape.ebookshop.services.dao.IEntityManager;
import io.github.gchape.ebookshop.services.dao.user.UserCrudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final IEntityManager<User> userCrudService;

    @Autowired
    public SignupServlet(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/signup.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var firstName = req.getParameter("firstName").trim();
        var lastName = req.getParameter("lastName").trim();
        var password = req.getParameter("password").trim();
        var email = req.getParameter("email").trim();

        userCrudService.save(new User(firstName, lastName, email, password));

        resp.sendRedirect("/");
    }
}
