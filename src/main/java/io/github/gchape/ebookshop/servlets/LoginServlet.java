package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.services.dao.IUserSqlService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final IUserSqlService userSqlService;

    @Autowired
    public LoginServlet(IUserSqlService userSqlService) {
        this.userSqlService = userSqlService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var email = req.getParameter("email").trim();
        var password = req.getParameter("password").trim();

        var user = userSqlService.findByEmail(email);

        if (user.isPresent() && BCrypt.checkpw(password, user.get().getPassword())) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user.get().getUsername());

            resp.sendRedirect("/");
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect email or password");
        }
    }
}
