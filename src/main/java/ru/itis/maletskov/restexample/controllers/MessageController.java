package ru.itis.maletskov.restexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.itis.maletskov.restexample.models.Message;
import ru.itis.maletskov.restexample.security.details.UserDetailsImpl;
import ru.itis.maletskov.restexample.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Message> getMessage(@PathVariable("id") Long id) {
        Message message = messageService.findOne(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        messageService.save(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messages = messageService.findAll();
        if (messages == null || messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(messageService.findAll());
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message, UriComponentsBuilder builder) {
        HttpHeaders headers = new HttpHeaders();
        if (message == null || message.getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (messageService.findOne(message.getId()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        messageService.update(message);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        if (messageService.findOne(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        messageService.delete(id);
        return ResponseEntity.ok().build();
    }
}
