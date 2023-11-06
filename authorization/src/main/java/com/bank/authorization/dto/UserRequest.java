package com.bank.authorization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Форма запоса на предоставление пользователя")
public class UserRequest {

    @Schema(description = "profileId пользователя")
    private Long profileId;
    @Schema(description = "password пользователя")
    private String password;
}
