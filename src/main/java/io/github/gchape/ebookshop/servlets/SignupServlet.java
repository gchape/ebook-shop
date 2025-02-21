package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.entities.User;
import io.github.gchape.ebookshop.services.dao.IEntityManager;
import io.github.gchape.ebookshop.services.dao.user.UserCrudService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
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
        req.getRequestDispatcher("signup.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var name = req.getParameter("name");
        var email = req.getParameter("email");
        var password = req.getParameter("password");

        var salt = BCrypt.gensalt();
        User user = new User(name, email, BCrypt.hashpw(password, salt));

        userCrudService.save(user);

        HttpSession session = req.getSession();
        session.setAttribute("user", user.getUsername());

        resp.sendRedirect("/");
    }
}
