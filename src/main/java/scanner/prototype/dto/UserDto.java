package scanner.prototype.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.prototype.model.User;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    public UserDto(final User user){
        this.id = user.getId();
    }

    public UserDto toDto(User rule) {
        return UserDto.builder()
                    .id(rule.getId())
                    .build();
    }
}
