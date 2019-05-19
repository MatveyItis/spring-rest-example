package ru.itis.maletskov.restexample.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    private String value;

    public static TokenDto from(String token) {
        return TokenDto.builder()
                .value(token)
                .build();
    }
}