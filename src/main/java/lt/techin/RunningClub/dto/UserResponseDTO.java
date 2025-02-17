package lt.techin.RunningClub.dto;

import lt.techin.RunningClub.model.Role;

import java.util.List;

public record UserResponseDTO(
    long id,
    String username,
    List<Role> roles
) {
}
