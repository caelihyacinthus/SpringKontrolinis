package lt.techin.RunningClub.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.RunningClub.model.User;

public record RegistrationRequestDTO(
    @NotNull
    User user
) {
}
