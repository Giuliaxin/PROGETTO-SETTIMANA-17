package giulianapetricore.progettosettimana17.controllers;

import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.payloads.AssignRoleDTO;
import giulianapetricore.progettosettimana17.services.UsersService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public User changeRoleById(@PathVariable UUID userId, @RequestBody AssignRoleDTO body) {
        return this.usersService.findByIdAndUpdateRole(userId, body);
    }
}