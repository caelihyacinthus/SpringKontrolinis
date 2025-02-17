package lt.techin.RunningClub.controller;

import lt.techin.RunningClub.dto.RunningEventMapper;
import lt.techin.RunningClub.dto.RunningEventRequestDTO;
import lt.techin.RunningClub.dto.RunningEventResponseDTO;
import lt.techin.RunningClub.model.RunningEvent;
import lt.techin.RunningClub.service.RunningEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RunningEventController {
    RunningEventService runningEventService;

    @Autowired
    public RunningEventController(RunningEventService runningEventService) {
        this.runningEventService = runningEventService;
    }

    @GetMapping("/events")
    public ResponseEntity<List<RunningEventResponseDTO>> getEvents() {
        return ResponseEntity.ok(RunningEventMapper.toRunningEventResposeDTOList(runningEventService.findAllEvents()));
    }

    @PostMapping("/events")
    public ResponseEntity<RunningEventResponseDTO> createEvent(@RequestBody RunningEventRequestDTO runningEventRequestDTO) {
        RunningEvent runningEvent = RunningEventMapper.toRunningEvent(runningEventRequestDTO);
        RunningEventResponseDTO savedEvent = RunningEventMapper.toRunningEventResponseDTO(runningEventService.saveEvent(runningEvent));
        return ResponseEntity.ok(savedEvent);
    }
}
