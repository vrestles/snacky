package ru.kazakova.snacky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.model.security.UserAttempts;

public interface UserAttemptsRepository extends JpaRepository<UserAttempts, Long> {

    UserAttempts findByUser(User user);
}
