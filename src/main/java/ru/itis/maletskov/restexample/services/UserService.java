package ru.itis.maletskov.restexample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.maletskov.restexample.models.Token;
import ru.itis.maletskov.restexample.models.User;
import ru.itis.maletskov.restexample.models.dto.LoginDto;
import ru.itis.maletskov.restexample.models.dto.TokenDto;
import ru.itis.maletskov.restexample.repositories.TokenRepository;
import ru.itis.maletskov.restexample.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private TokenRepository tokenRepository;
    private PasswordEncoder encoder;
    private UserRepository userRepository;

    @Value("${token.expired}")
    private Integer expiredSecondsForToken;

    @Autowired
    public UserService(TokenRepository tokenRepository,
                       PasswordEncoder encoder,
                       UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public TokenDto login(LoginDto loginDto) {
        Optional<User> userCandidate = userRepository.findFirstByUsernameIgnoreCase(loginDto.getUsername());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            if (encoder.matches(loginDto.getPassword(), user.getPassword())) {
                String value = UUID.randomUUID().toString();
                Token token = new Token();
                token.setCreatedAt(LocalDateTime.now());
                token.setExpiredDateTime(LocalDateTime.now().plusSeconds(expiredSecondsForToken));
                token.setValue(value);
                token.setUser(user);
                tokenRepository.save(token);
                return TokenDto.from(value);
            }
        }
        throw new BadCredentialsException("Incorrect login or password");
    }
}
