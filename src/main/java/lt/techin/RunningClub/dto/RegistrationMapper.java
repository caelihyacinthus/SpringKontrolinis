package lt.techin.RunningClub.dto;

import lt.techin.RunningClub.model.Registration;

public class RegistrationMapper {
    public static RegistrationResponseDTO toRegistrationResponseDTO(Registration registration) {
        return new RegistrationResponseDTO(registration.getId(), registration.getRunningEvent().getName(), registration.getUser().getId(), registration.getRegistrationDate());
    }
}
