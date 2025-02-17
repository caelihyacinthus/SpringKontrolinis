package lt.techin.RunningClub.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.RunningClub.model.Role;

import java.util.List;

public record UserRequestDTO(
    @Size(min = 4, max = 100, message = "must be between 4 and 100 characters")
    @Pattern(regexp = "[a-z0-9]+", message = "must only contain lowercase and numbers")
    String username,
    @Size(min = 6, message = "must be longer than 6 characters")
    String password,
    @NotEmpty
    List<Role> roles
) {
}
