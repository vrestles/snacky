package ru.kazakova.snacky.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kazakova.snacky.model.security.Role;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.model.security.UserAttempts;
import ru.kazakova.snacky.repository.RoleRepository;
import ru.kazakova.snacky.repository.UserAttemptsRepository;
import ru.kazakova.snacky.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdministrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserFullInfo userFullInfo;
    private final UserAttemptsRepository userAttemptsRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(User userDetails) {
        User user = new User(userDetails.getLogin(), passwordEncoder.encode(userDetails.getPassword()));
        Role role = roleRepository.findByName("USER");
        user.setRole(role);
        UserAttempts userAttempts = new UserAttempts(user, 0, LocalDateTime.now());
        userRepository.save(user);
        userAttemptsRepository.save(userAttempts);
    }

    @Transactional
    public void unlockUser(String login) {
        userFullInfo.resetFailAttempts(login, true);
    }

    @Transactional
    public void grantRole(String login, String roleName) {
        User user = userRepository.findByLogin(login);
        Role role = roleRepository.findByName(roleName);
        user.setRole(role);
        userRepository.save(user);
    }
}
