package giulianapetricore.progettosettimana17.controllers;

import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.ValidationException;
import giulianapetricore.progettosettimana17.payloads.AssignRoleDTO;
import giulianapetricore.progettosettimana17.services.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public User changeRoleById(@PathVariable UUID userId, @RequestBody @Validated AssignRoleDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new ValidationException(errorsList);
        }
        return this.usersService.findByIdAndUpdateRole(userId, body);
    }
}