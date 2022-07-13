package ru.kazakova.snacky.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Component;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.model.security.UserAttempts;
import ru.kazakova.snacky.repository.UserAttemptsRepository;
import ru.kazakova.snacky.repository.UserRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserFullInfoImpl implements UserFullInfo {

    private final UserRepository userRepository;
    private final UserAttemptsRepository userAttemptsRepository;

    private static final int MAX_ATTEMPTS = 3;

    @Override
    public void updateFailAttempts(String username) {
        User user = userRepository.findByLogin(username);
        if (user != null) {
            UserAttempts userAttempts = userAttemptsRepository.findByUser(user);
            int updatedAttemptCount = userAttempts.getAttempts() + 1;
            userAttempts.setAttempts(updatedAttemptCount);
            userAttempts.setLastModified(LocalDateTime.now());
            userAttemptsRepository.save(userAttempts);

            if (updatedAttemptCount >= MAX_ATTEMPTS) {
                user.setAccountNonLocked(false);
                userRepository.save(user);
                throw new LockedException("User Account is locked!");
            }
        }
    }

    @Override
    public void resetFailAttempts(String username, boolean unlockUser) {
        User user = userRepository.findByLogin(username);
        UserAttempts userAttempts = userAttemptsRepository.findByUser(user);
        userAttempts.setAttempts(0);
        userAttempts.setLastModified(LocalDateTime.now());
        userAttemptsRepository.save(userAttempts);
        if (unlockUser) {
            user.setAccountNonLocked(true);
            userRepository.save(user);
        }
    }

    @Override
    public UserAttempts getUserAttempts(String username) {
        User user = userRepository.findByLogin(username);
        return userAttemptsRepository.findByUser(user);
    }
}
