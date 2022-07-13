package ru.kazakova.snacky.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.kazakova.snacky.model.security.User;
import ru.kazakova.snacky.service.security.AdministrationService;

@RestController
@RequiredArgsConstructor
@Tag(name = "Controller for administrative operations")
public class AdministrationController {

    private final AdministrationService administrationService;

    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void register(@RequestBody User userDetails) {
        administrationService.register(userDetails);
    }

    @GetMapping("/unlock")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void unlockUser(@RequestParam String login) {
        administrationService.unlockUser(login);
    }

    @GetMapping("/grantRole")
    @ResponseStatus(code = HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void grantRole(@RequestParam String role,
                          @RequestParam String login) {
        administrationService.grantRole(login, role);
    }
}
