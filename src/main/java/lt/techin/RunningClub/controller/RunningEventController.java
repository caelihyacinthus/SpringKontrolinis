package lt.techin.RunningClub.controller;

import lt.techin.RunningClub.dto.*;
import lt.techin.RunningClub.model.Registration;
import lt.techin.RunningClub.model.RunningEvent;
import lt.techin.RunningClub.model.User;
import lt.techin.RunningClub.service.RegistrationService;
import lt.techin.RunningClub.service.RunningEventService;
import lt.techin.RunningClub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RunningEventController {
    RunningEventService runningEventService;
    UserService userService;
    RegistrationService registrationService;

    @Autowired
    public RunningEventController(RunningEventService runningEventService, UserService userService, RegistrationService registrationService) {
        this.runningEventService = runningEventService;
        this.userService = userService;
        this.registrationService = registrationService;
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

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id) {
        if (runningEventService.existsEventById(id)) {
            //already checked that it exists by id, would not be null
            runningEventService.deleteEvent(runningEventService.findEventById(id).get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/events/{eventId}/register")
    public ResponseEntity<?> registerForEvent(@PathVariable long eventId, @RequestBody RegistrationRequestDTO registrationRequestDTO) {
        Optional<RunningEvent> eventOptional = runningEventService.findEventById(eventId);
        Optional<User> userOptional = userService.findUserById(registrationRequestDTO.user().getId());
        if (eventOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        RunningEvent runningEvent = eventOptional.get();
        User user = userOptional.get();

        List<Registration> userReg = user.getRegistrations();
        for (Registration r : userReg) {
            if (r.getRunningEvent() == runningEvent) {
                return ResponseEntity.badRequest().build();
            }
        }

        Registration registration = new Registration();
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setUser(user);
        registration.setRunningEvent(runningEvent);

        RegistrationResponseDTO savedRegistration = RegistrationMapper.toRegistrationResponseDTO(registrationService.saveRegistration(registration));

        return ResponseEntity.ok(savedRegistration);
    }
}
