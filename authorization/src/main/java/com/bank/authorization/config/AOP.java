package com.bank.authorization.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.security.Principal;
import java.sql.Timestamp;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AOP {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Around("execution(* com.bank.authorization.controllers.UserController.save*(..))")
    public Object saveUser(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Записываем данные переданные через метод saveUser() в таблицу audit");
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Object obj = joinPoint.proceed();
        jdbcTemplate.update(
                "INSERT INTO authorizations.audit (created_at, entity_type, operation_type, created_by, new_entity_json, entity_json) VALUES (?, ?, ?, ?, ?, ?)",
                new Timestamp(System.currentTimeMillis()),
                obj.getClass().getName(),
                "POST",
                principal.getName(),
                objectMapper.writeValueAsString(obj),
                objectMapper.writeValueAsString(obj));
        return obj;
    }

    @Around("execution(* com.bank.authorization.controllers.UserController.get*(..))")
    public Object getUser(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Записываем данные переданные через метод getUser() в таблицу audit");
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Object object = joinPoint.proceed();
        jdbcTemplate.update(
                "INSERT INTO authorizations.audit (created_at, entity_type, operation_type, created_by, new_entity_json, entity_json) VALUES (?, ?, ?, ?, ?, ?)",
                new Timestamp(System.currentTimeMillis()),
                object.getClass().getName(),
                "GET",
                principal.getName(),
                objectMapper.writeValueAsString(object),
                objectMapper.writeValueAsString(object));
        return object;

    }
    @Around("execution(* com.bank.authorization.controllers.UserController.create*(..))")
    public Object createToken(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Записываем данные переданные через метод createToken() в таблицу audit");
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Object obj = joinPoint.proceed();
        jdbcTemplate.update(
                "INSERT INTO authorizations.audit (created_at, entity_type, modified_at, operation_type, created_by, new_entity_json, entity_json) VALUES (?, ?, ?, ?, ?, ?,?)",
                new Timestamp(System.currentTimeMillis()),
                obj.getClass().getName(),
                new Timestamp(System.currentTimeMillis()),
                "POST",
                principal.getName(),
                objectMapper.writeValueAsString(obj),
                objectMapper.writeValueAsString(obj));
        return obj;
    }
}
