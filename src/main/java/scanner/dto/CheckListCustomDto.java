package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.model.CustomRule;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListCustomDto {
    
    private String customDetail;

    public static CheckListCustomDto toDto(CustomRule rule) {
        return CheckListCustomDto.builder()
                                .customDetail(rule.getCustomDetail())
                                .build();
    }
}
