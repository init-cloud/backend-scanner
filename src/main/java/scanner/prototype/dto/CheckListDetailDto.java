package scanner.prototype.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CheckListDetailDto {
    
    private String ruleId;
    private String ruleOnOff;
    private UserDto user;
}
