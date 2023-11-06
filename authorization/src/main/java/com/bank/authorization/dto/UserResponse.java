package com.bank.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Форма которяа возвращается в качестве ответа на запрос об пользователе в БД")
public class UserResponse {

    @Schema(description = "Права доступа пользователя")
    private String role;
    @Schema(description = "profileId доступа пользователя")
    private Long profileId;
    @Schema(description = "password доступа пользователя")
    private String password;

    public UserResponse() {

    }
}
