package giulianapetricore.progettosettimana17.controllers;

import giulianapetricore.progettosettimana17.entities.User;
import giulianapetricore.progettosettimana17.exceptions.ValidationException;
import giulianapetricore.progettosettimana17.payloads.LoginDTO;
import giulianapetricore.progettosettimana17.payloads.LoginRespDTO;
import giulianapetricore.progettosettimana17.payloads.NewUserDTO;
import giulianapetricore.progettosettimana17.payloads.NewUserRespDTO;
import giulianapetricore.progettosettimana17.services.AuthService;
import giulianapetricore.progettosettimana17.services.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsersService usersService;

    public AuthController(AuthService authService, UsersService usersService) {
        this.authService = authService;
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new ValidationException(errorsList);
        }
        String accessToken = this.authService.checkCredentialsAndGenerateToken(body);
        return new LoginRespDTO(accessToken);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO createUser(@RequestBody @Validated NewUserDTO payload, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new ValidationException(errorsList);
        }
        User utenteCreato = this.usersService.create(payload);
        return new NewUserRespDTO(utenteCreato.getUserId());
    }
}