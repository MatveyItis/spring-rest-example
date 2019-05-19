package ru.itis.maletskov.restexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.maletskov.restexample.models.dto.LoginDto;
import ru.itis.maletskov.restexample.models.dto.TokenDto;
import ru.itis.maletskov.restexample.services.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginData) {
        return ResponseEntity.ok(service.login(loginData));
    }
}
