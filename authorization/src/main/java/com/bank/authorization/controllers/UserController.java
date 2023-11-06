package com.bank.authorization.controllers;

import com.bank.authorization.dto.*;
import com.bank.authorization.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name="UserController", description="Создание новых пользователей и авторизация существующих пользователей")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping("/new")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Регистрация пользователя",
            description = "Позволяет зарегистрировать пользователя"
    )
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Parameter(description = "Объект запроса для создания нового пользователя") UserRequestForNewUser userRequest) {
        return userService.saveUser(userRequest);
    }


    @GetMapping("/admin")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Информация для пользователя с ролью ADMIN",
            description = "Позволяет получить доступ к нужной для пользователя информации"
    )
    public String adminInfo() {
        return "Admin info";
    }


    @GetMapping("/user")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Информация для пользователя с ролью USER",
            description = "Позволяет получить доступ к нужной для пользователя информации"
    )
    public String userInfo() {
        return "User info";
    }


    @PostMapping("/auth")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Предоставляет пользователю права доступа"
    )
    public ResponseEntity<Token> createToken(@RequestBody @Parameter(description = "Объект запроса для получения токена") UserRequestForToken userRequest) {
       return userService.createToken(userRequest);
    }


    @GetMapping("/get")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение пользователя",
            description = "Предоставляет информацию о пользователе находящимся в БД"
    )
    public ResponseEntity<UserResponse> getUser(@RequestBody @Parameter(description = "Объект запроса для получения пользователя")
                                                UserRequest userRequest) {
        return userService.findUser(userRequest.getProfileId(), userRequest.getPassword());
    }


    @GetMapping("/info")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение имени пользователя",
            description = "Предоставляет информацию об имени пользователя находящегося в SecurityContextHolder"
    )
    public String userData(Principal principal) {
        return "This project create --> : " + principal.getName();
    }

    @GetMapping("/users")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение всех пользователей",
            description = "Предоставляет информацию обо всех пользователях находящихся в БД"
    )
    public ResponseEntity<?> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

}
