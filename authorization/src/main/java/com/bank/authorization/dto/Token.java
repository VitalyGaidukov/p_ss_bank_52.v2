package com.bank.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Предоставление токена")
public class Token {

    @Schema(description = "Поле размещения токена")
    private String token;
}
