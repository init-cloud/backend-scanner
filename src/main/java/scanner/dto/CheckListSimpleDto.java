package scanner.dto;


import lombok.*;
import scanner.model.CustomRule;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
                            .ruleOnOff(dto.getRuleOnOff())
                            .customDetail(null)
                            .build();

        return CustomRule.builder()
                        .ruleOnOff(dto.getRuleOnOff())
                        .customDetail(dto.getCustom().getCustomDetail())
                        .build();
    }
}
