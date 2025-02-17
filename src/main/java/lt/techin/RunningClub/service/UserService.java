package lt.techin.RunningClub.service;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.RunningClub.model.User;
import lt.techin.RunningClub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUsernameUnique(String username) {
        return userRepository.existsByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
