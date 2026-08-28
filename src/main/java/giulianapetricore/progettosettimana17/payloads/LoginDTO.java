package giulianapetricore.progettosettimana17.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @Email(message = "L'indirizzo inserito non è un email valida") String email,
        @NotBlank(message = "La password è obbligatoria") String password
) {}