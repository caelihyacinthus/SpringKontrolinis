package lt.techin.RunningClub.dto;

import java.time.LocalDateTime;

public record RegistrationResponseDTO(
    long id,
    String eventName,
    long userId,
    LocalDateTime registrationDate
) {
}
