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
        if(dto.getCustom() == null)
            return CustomRule.builder()
                            .id(dto.getId())
                            .ruleOnOff(dto.getRuleOnOff())
                            .customDetail(null)
                            .modifiedAt(LocalDateTime.now())
                            .build();

        return CustomRule.builder()
                        .id(dto.getId())
                        .ruleOnOff(dto.getRuleOnOff())
                        .customDetail(dto.getCustom().getCustomDetail())
                        .modifiedAt(LocalDateTime.now())
                        .build();
    }
}
