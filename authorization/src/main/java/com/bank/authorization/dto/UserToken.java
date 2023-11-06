package com.bank.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Форма на основании которой создается токен")
public class UserToken {

    @Schema(description = "username на пользователя")
    private String username;
    @Schema(description = "Правадоступа пользователя")
    private List<String> roles;

}
