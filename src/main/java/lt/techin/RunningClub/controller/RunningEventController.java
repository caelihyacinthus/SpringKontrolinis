package lt.techin.RunningClub.controller;

import jakarta.validation.Valid;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/events/{id}/participants")
    public ResponseEntity<?> getEventById(@PathVariable long id) {
        if (runningEventService.existsEventById(id)) {
            List<Registration> registration = runningEventService.findEventById(id).get().getRegistrations();
            List<UserEventResponseDTO> usersDTO = registration.stream().map(Registration::getUser).map(UserMapper::toUserEventResponseDTO).toList();
            return ResponseEntity.ok(usersDTO);

        } else {
            Map<String, String> badResponse = new HashMap<>();
            badResponse.put("event", "event does not exists");
            return ResponseEntity.badRequest().body(badResponse);
        }
    }

    @PostMapping("/events")
    public ResponseEntity<RunningEventResponseDTO> createEvent(@Valid @RequestBody RunningEventRequestDTO runningEventRequestDTO) {
        RunningEvent runningEvent = RunningEventMapper.toRunningEvent(runningEventRequestDTO);
        RunningEventResponseDTO savedEvent = RunningEventMapper.toRunningEventResponseDTO(runningEventService.saveEvent(runningEvent));


        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(savedEvent.id())
                    .toUri())
            .body(savedEvent);
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
            Map<String, String> badResponse = new HashMap<>();
            badResponse.put("user", "user does not exists");
            return ResponseEntity.badRequest().body(badResponse);
        }
        RunningEvent runningEvent = eventOptional.get();
        User user = userOptional.get();

        List<Registration> userReg = user.getRegistrations();
        for (Registration r : userReg) {
            if (r.getRunningEvent() == runningEvent) {
                Map<String, String> badResponse = new HashMap<>();
                badResponse.put("user", "user already registered");
                return ResponseEntity.badRequest().body(badResponse);
            }
        }

        Registration registration = new Registration();
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setUser(user);
        registration.setRunningEvent(runningEvent);

        RegistrationResponseDTO savedRegistration = RegistrationMapper.toRegistrationResponseDTO(registrationService.saveRegistration(registration));

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedRegistration.id())
                    .toUri())
            .body(savedRegistration);
    }
}
