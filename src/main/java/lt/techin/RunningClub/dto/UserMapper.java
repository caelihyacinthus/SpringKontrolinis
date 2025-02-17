package lt.techin.RunningClub.dto;

import lt.techin.RunningClub.model.User;

public class UserMapper {
    public static User toUser(UserRequestDTO userRequestDTO) {
        return new User(userRequestDTO.username(), userRequestDTO.password(), userRequestDTO.roles());
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
    }
}
