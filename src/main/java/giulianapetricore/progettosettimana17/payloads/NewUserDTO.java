package giulianapetricore.progettosettimana17.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record NewUserDTO(
        @NotBlank(message = "L'username è obbligatorio") String username,
        @NotBlank(message = "Il nome completo è obbligatorio") String fullName,
        @Email(message = "L'indirizzo inserito non è un email valida") String email,
        @Length(min = 4, message = "La password deve essere di almeno 4 caratteri") String password
) {}