package io.github.gchape.ebookshop.servlets;

import io.github.gchape.ebookshop.services.SignupService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final SignupService signupService;

    public SignupServlet(SignupService signupService) {
        this.signupService = signupService;
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
        var username = signupService.registerUser(name, email, password);

        if (username != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", username);

            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/signup");
        }
    }
}
