package scanner.prototype.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.CustomRule;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListCustomDto {
    
    private String custom;

    public static CheckListCustomDto toDto(CustomRule rule) {
        return CheckListCustomDto.builder()
                                .custom(rule.getCustomDetail())
                                .build();
    }
}
