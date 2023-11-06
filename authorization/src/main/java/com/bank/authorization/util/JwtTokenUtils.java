package com.bank.authorization.util;


import com.bank.authorization.dto.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenUtils {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.lifetime}")
    private Duration lifeTime;

    public String generateToken(UserToken userToken){
        log.info("Генерируем токен");
        String userName = userToken.getUsername();
        List<String> roles = userToken.getRoles();

        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + lifeTime.toMillis());
        return Jwts.builder()
                .claim("roles",roles)
                .setSubject(userName)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String getUsername(String token){
        log.info("Находим имя пользователя в теле токена");
        return getAllClaims(token).getSubject();
    }

    public List<String> getRoles(String token){
        log.info("Находим роли в теле токена");
        return getAllClaims(token).get("roles", List.class);
    }

    private Claims getAllClaims(String token){
        log.info("Возвращаем данные внесенные в токен");
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
