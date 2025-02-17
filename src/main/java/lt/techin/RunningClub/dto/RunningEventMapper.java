package lt.techin.RunningClub.dto;

import lt.techin.RunningClub.model.RunningEvent;

import java.util.ArrayList;
import java.util.List;

public class RunningEventMapper {

    public static RunningEventResponseDTO toRunningEventResponseDTO(RunningEvent event) {
        return new RunningEventResponseDTO(event.getId(), event.getName(), event.getCalendarDate(), event.getLocation(), event.getMaxParticipants());
    }

    public static List<RunningEventResponseDTO> toRunningEventResposeDTOList(List<RunningEvent> events) {
        List<RunningEventResponseDTO> eventsDTO = new ArrayList<>();
        for (RunningEvent re : events) {
            eventsDTO.add(toRunningEventResponseDTO(re));
        }
        return eventsDTO;
    }
}
