package io.github.gchape.ebookshop.services;

import io.github.gchape.ebookshop.entities.User;
import io.github.gchape.ebookshop.repositories.IUserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {
    private final IUserRepository userRepository;

    @Autowired
    public SignupService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<String> registerUser(String name, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            return Optional.empty();
        }

        var hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        var user = new User(name, email, hashedPassword);
        userRepository.save(user);

        return Optional.of(user.getUsername());
    }
}
