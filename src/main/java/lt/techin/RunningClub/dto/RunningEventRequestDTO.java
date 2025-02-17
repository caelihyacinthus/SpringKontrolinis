package lt.techin.RunningClub.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RunningEventRequestDTO(
    @NotBlank
    @Size(min = 5, max = 255, message = "must be between 5 and 255 characters")
    String name,
    @NotNull
    @Future
    LocalDate calendarDate,
    @NotBlank
    @Pattern(regexp = "[A-Za-z0-9]+", message = "must only contain letters and numbers")
    String location,
    @Min(value = 0, message = "must be more than 0")
    int maxParticipants
) {
}
