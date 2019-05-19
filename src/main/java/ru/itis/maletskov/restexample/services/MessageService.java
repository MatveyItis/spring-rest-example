package ru.itis.maletskov.restexample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.maletskov.restexample.models.Message;
import ru.itis.maletskov.restexample.repositories.MessageRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(Message message) {
        message.setDate(Date.valueOf(LocalDate.now()));
        messageRepository.save(message);
    }

    public void update(Message message) {
        this.save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    public Message findOne(Long id) {
        return messageRepository.findById(id).orElse(null);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
