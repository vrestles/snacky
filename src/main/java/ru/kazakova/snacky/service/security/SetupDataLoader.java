package ru.kazakova.snacky.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kazakova.snacky.model.security.Role;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.model.security.UserAttempts;
import ru.kazakova.snacky.repository.RoleRepository;
import ru.kazakova.snacky.repository.UserAttemptsRepository;
import ru.kazakova.snacky.repository.UserRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final String INITIAL_ADMIN_LOGIN = "admin";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAttemptsRepository userAttemptsRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        if (userRepository.findByLogin(INITIAL_ADMIN_LOGIN) != null) {
            return;
        }
        Role adminRole = roleRepository.findByName("ADMIN");
        User admin = new User(INITIAL_ADMIN_LOGIN, "$2a$10$F3tuRTfb7okluAALfPJZ2.R9xHqzHKdtrGsROIVe4qEzukW7I167m");
        admin.setRole(adminRole);
        UserAttempts userAttempts = new UserAttempts(admin, 0, LocalDateTime.now());
        userRepository.save(admin);
        userAttemptsRepository.save(userAttempts);

        alreadySetup = true;
    }
}
