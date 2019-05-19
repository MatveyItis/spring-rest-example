package ru.itis.maletskov.restexample.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.maletskov.restexample.models.Token;
import ru.itis.maletskov.restexample.repositories.TokenRepository;

import java.util.Optional;

@Service(value = "tokenUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private TokenRepository tokenRepository;

    @Autowired
    public UserDetailsServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
        Optional<Token> authenticationCandidate = tokenRepository.findFirstByValue(value);
        if (authenticationCandidate.isPresent()) {
            Token token = authenticationCandidate.get();
            return new UserDetailsImpl(token.getUser(), token);
        } return null;
    }
}
