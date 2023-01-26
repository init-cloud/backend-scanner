package scanner.dto;

import lombok.*;

import scanner.model.CustomRule;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListCustomDto {
    
    private String customDetail;

    public static CheckListCustomDto toDto(CustomRule rule) {
        return CheckListCustomDto.builder()
                                .customDetail(rule.getCustomDetail())
                                .build();
    }
}
