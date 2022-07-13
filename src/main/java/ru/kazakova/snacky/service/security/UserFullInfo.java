package ru.kazakova.snacky.service.security;

import ru.kazakova.snacky.model.security.UserAttempts;

public interface UserFullInfo {

    void updateFailAttempts(String username);
    void resetFailAttempts(String username, boolean unlockUser);
    UserAttempts getUserAttempts(String username);
}
