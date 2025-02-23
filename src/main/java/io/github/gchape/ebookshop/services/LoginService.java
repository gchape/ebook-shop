package io.github.gchape.ebookshop.services;

import io.github.gchape.ebookshop.repositories.IUserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final IUserRepository userRepository;

    @Autowired
    public LoginService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String authenticate(String email, String password) {
        var user = userRepository.findByEmail(email);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user.getUsername();
        }

        return null;
    }
}
