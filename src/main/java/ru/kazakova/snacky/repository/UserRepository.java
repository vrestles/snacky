package ru.kazakova.snacky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kazakova.snacky.model.security.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
