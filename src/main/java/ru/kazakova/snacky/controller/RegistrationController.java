package ru.kazakova.snacky.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for app user registration")
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody User userDetails) {
        User user = User.builder()
                .login(userDetails.getLogin())
                .password(passwordEncoder.encode(userDetails.getPassword()))
                .build();
        userRepository.save(user);
    }
}
