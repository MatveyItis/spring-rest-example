package ru.itis.maletskov.restexample.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.maletskov.restexample.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
