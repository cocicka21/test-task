package by.artur.testtask.services;

import by.artur.testtask.dtos.EmailRequest;
import by.artur.testtask.dtos.JwtResponse;
import by.artur.testtask.dtos.PhoneRequest;
import by.artur.testtask.entities.User;
import by.artur.testtask.repositories.UserRepository;
import by.artur.testtask.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Аутентификация пользователя по email и паролю.
     */
    public JwtResponse authenticateByEmail(EmailRequest emailRequest) {
        Optional<User> userOptional = userRepository.findByEmails_Email(emailRequest.getEmail());
        return new JwtResponse(getString(emailRequest.getPassword(), userOptional));
    }

    /**
     * Аутентификация пользователя по phone и паролю.
     */
    public JwtResponse authenticateByPhone(PhoneRequest phoneRequest) {
        Optional<User> userOptional = userRepository.findByPhones_Phone(phoneRequest.getPhone());
        return new JwtResponse(getString(phoneRequest.getPassword(), userOptional));
    }

    private String getString(String password, Optional<User> userOptional) {
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return JWTUtil.generateToken(user.getId());
    }

}
