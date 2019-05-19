package ru.itis.maletskov.restexample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.maletskov.restexample.models.Token;
import ru.itis.maletskov.restexample.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findFirstByValue(String value);

    void deleteTokensByExpiredDateTimeBefore(LocalDateTime now);
}
