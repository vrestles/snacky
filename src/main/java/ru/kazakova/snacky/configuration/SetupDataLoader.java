package ru.kazakova.snacky.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kazakova.snacky.model.security.Role;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.repository.RoleRepository;
import ru.kazakova.snacky.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        Role adminRole = roleRepository.findByName("ADMIN");
        User admin = new User("admin", "$2a$10$F3tuRTfb7okluAALfPJZ2.R9xHqzHKdtrGsROIVe4qEzukW7I167m");
        admin.setRole(adminRole);
        userRepository.save(admin);

        alreadySetup = true;
    }
}
