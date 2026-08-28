package giulianapetricore.progettosettimana17.payloads;

import jakarta.validation.constraints.NotBlank;

public record NewPostDTO(
        @NotBlank(message = "Il testo del post è obbligatorio") String text
) {}