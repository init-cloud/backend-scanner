package scanner.prototype.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.CustomRule;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckListSimpleDto {
    
    private Long id;
    private String ruleId;
    private String ruleOnOff;

    public CheckListSimpleDto(final CustomRule rule){
        this.id = rule.getId();
        this.ruleId = rule.getRuleId();
        this.ruleOnOff = rule.getRuleOnOff();
    }

    public static CheckListSimpleDto toDto(CustomRule rule) {
        return CheckListSimpleDto.builder()
                                .id(rule.getId())
                                .ruleId(rule.getRuleId())
                                .ruleOnOff(rule.getRuleOnOff())
                                .build();
    }

    public static CustomRule toEntity(final CheckListSimpleDto dto){
        return CustomRule.builder()
                .id(dto.getId())
                .ruleId(dto.getRuleId())
                .ruleOnOff(dto.getRuleOnOff())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
