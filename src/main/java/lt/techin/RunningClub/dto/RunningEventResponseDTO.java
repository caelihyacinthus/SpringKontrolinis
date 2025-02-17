package lt.techin.RunningClub.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RunningEventResponseDTO(
    long id,
    String name,
    LocalDate calendarDate,
    String location,
    int maxParticipants
) {
}
