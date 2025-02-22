package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.services.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final LoginService loginService;

    @Autowired
    public LoginServlet(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var email = req.getParameter("email").trim();
        var password = req.getParameter("password").trim();
        var username = loginService.authenticate(email, password);

        if (username.isPresent()) {
            HttpSession session = req.getSession(false);
            if (session == null) {
                session = req.getSession(true);
            }

            synchronized (session) {
                session.setAttribute("user", username);
                resp.sendRedirect("/");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid email or password");
        }
    }
}
