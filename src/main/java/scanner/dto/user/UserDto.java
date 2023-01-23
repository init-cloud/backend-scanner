package scanner.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class UserDto {
    private String username;
    private LocalDateTime lastLogin;
}
