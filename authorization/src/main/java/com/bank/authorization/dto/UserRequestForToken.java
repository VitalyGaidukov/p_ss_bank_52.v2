package com.bank.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Форма для получения токена зарегистрированного пользователя")
public class UserRequestForToken {

    @Schema(description = "username зарегистрированного пользователя")
    private String username;
    @Schema(description = "profileId зарегистрированного пользователя")
    private Long profileId;
    @Schema(description = "password зарегистрированного пользователя")
    private String password;

}
