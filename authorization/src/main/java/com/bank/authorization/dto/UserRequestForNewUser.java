package com.bank.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Форма запроса на добавления пользователя")
public class UserRequestForNewUser {

    @Schema(description = "username нового пользователя")
    private String username;
    @Schema(description = "profileId нового пользователя")
    private Long profileId;
    @Schema(description = "password нового пользователя")
    private String password;
}
