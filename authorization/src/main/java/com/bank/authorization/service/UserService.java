package com.bank.authorization.service;

import com.bank.authorization.dto.*;
import com.bank.authorization.models.User;
import com.bank.authorization.repository.UserRepository;
import com.bank.authorization.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final UserRepository userRepository;

    public ResponseEntity<UserResponse> saveUser(UserRequestForNewUser userRequest) {
        log.info("Создаем нового юзера");
        User user = new User();
        if (userRequest.getProfileId() < 10) {
            user.setRole("ROLE_USER");
        }
        if (userRequest.getProfileId() > 10) {
            user.setRole("ROLE_ADMIN");
        }
        String password1 = passwordEncoder.encode(userRequest.getPassword());
        user.setProfileId(userRequest.getProfileId());
        user.setPassword(password1);
        userRepository.save(user);
        return ResponseEntity.ok(new UserResponse(user.getRole(), user.getProfileId(), user.getPassword()));
    }

    public ResponseEntity<Token> createToken(UserRequestForToken userRequest) {
        String username = userRequest.getUsername();
        User user = findUserForToken(userRequest.getProfileId(), userRequest.getPassword());
        UserToken userToken = new UserToken(username, List.of(user.getRole()));
        String token = jwtTokenUtils.generateToken(userToken);
        log.info("Создаем токен и возвращаем его");
        return ResponseEntity.ok(new Token(token));
    }

    public User findUserForToken(Long profileId, String password) {
        User user = new User();
        log.info("Находим юзера для создания токена по profileId и password");
        try {
            user = userRepository.findUserByProfileIdAndAndPassword(profileId, password);
        } catch (EntityNotFoundException e) {
            log.error("Пользователь с таким паролем не найден");
        }
        return user;
    }

    public ResponseEntity<?> findAllUsers() {
        log.info("Находим и возвращаем всех пользователей из БД");
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<UserResponse> findUser(Long profileId, String password) {
        log.info("Находим пользователя по profileId и password");
        UserResponse userResponse = new UserResponse();
        try {
            User user = userRepository.findUserByProfileIdAndAndPassword(profileId, password);
            userResponse.setPassword(user.getPassword());
            userResponse.setRole(user.getRole());
            userResponse.setProfileId(user.getProfileId());
        } catch (EntityNotFoundException e) {
            log.error("Пользователь с таким паролем не найден");
        }
        return ResponseEntity.ok(userResponse);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Активируем UserDetails в контексте приложения");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) auth.getPrincipal();
    }
}
