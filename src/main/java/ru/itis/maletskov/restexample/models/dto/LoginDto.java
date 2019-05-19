package ru.itis.maletskov.restexample.models.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
