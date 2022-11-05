package scanner.prototype.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.model.User;

@Service
@RequiredArgsConstructor
public class UserService {

    public User getUser(){

        return new User();
    }
}
