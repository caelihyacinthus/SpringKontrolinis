package lt.techin.RunningClub.controller;

import jakarta.validation.Valid;
import lt.techin.RunningClub.dto.UserMapper;
import lt.techin.RunningClub.dto.UserRequestDTO;
import lt.techin.RunningClub.dto.UserResponseDTO;
import lt.techin.RunningClub.model.User;
import lt.techin.RunningClub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userService.isUsernameUnique(userRequestDTO.username())) {
            return ResponseEntity.badRequest().build();//todo add msg that not unique
        }

        User user = UserMapper.toUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserResponseDTO savedUser = UserMapper.toUserResponseDTO(userService.saveUser(user));

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedUser.id())
                    .toUri())
            .body(savedUser);
    }
}
