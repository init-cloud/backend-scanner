package scanner.prototype.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.model.User;
import scanner.prototype.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
