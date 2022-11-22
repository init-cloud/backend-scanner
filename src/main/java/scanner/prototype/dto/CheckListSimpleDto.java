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
    private CheckListCustomDto custom;

    public CheckListSimpleDto(final CustomRule rule){
        this.id = rule.getId();
        this.ruleId = rule.getRuleId();
        this.ruleOnOff = rule.getRuleOnOff();
        this.custom = new CheckListCustomDto(rule.getCustomDetail());
    }

    public static CheckListSimpleDto toDto(CustomRule rule) {
        return CheckListSimpleDto.builder()
                                .id(rule.getId())
                                .ruleId(rule.getRuleId())
                                .ruleOnOff(rule.getRuleOnOff())
                                .custom(new CheckListCustomDto(rule.getCustomDetail()))
                                .build();
    }

    public static CustomRule toEntity(final CheckListSimpleDto dto){
        return CustomRule.builder()
                .id(dto.getId())
                .ruleOnOff(dto.getRuleOnOff())
                .defaultRuleId(dto.getRuleId())
                .customDetail(dto.getCustom().getCustom())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
