package ru.kazakova.snacky.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kazakova.snacky.model.security.UserAttempts;

import java.time.LocalDateTime;

@Component("customAuthenticationProvider")
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserFullInfo userFullInfo;

    @Qualifier("userDetailsService")
    @Autowired
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            Authentication auth = super.authenticate(authentication);
            userFullInfo.resetFailAttempts(authentication.getName(), false);
            return auth;
        } catch (BadCredentialsException e) {
            userFullInfo.updateFailAttempts(authentication.getName());
            throw e;
        } catch (LockedException e) {
            StringBuilder errorMessage = new StringBuilder(
                    String.format("User account with login <%s> is locked", authentication.getName()));
            UserAttempts userAttempts = userFullInfo.getUserAttempts(authentication.getName());

            if (userAttempts != null) {
                LocalDateTime lastAttempts = userAttempts.getLastModified();
                int attemptCount = userAttempts.getAttempts();
                errorMessage.append(String.format(" with <%d> attempts, last modification date: %s", attemptCount, lastAttempts));
            } else {
                errorMessage.append(". ").append(e.getMessage());
            }

            throw new LockedException(errorMessage.toString());
        }
    }
}
