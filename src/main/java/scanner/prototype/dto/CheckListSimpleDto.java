package scanner.prototype.dto;


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
    
    private String ruleId;
    private String ruleOnOff;

    public CheckListSimpleDto(final CustomRule rule){
        this.ruleId = rule.getRuleId();
        this.ruleOnOff = rule.getRuleOnOff();
    }

    public CheckListSimpleDto toDto(CustomRule rule) {
        return CheckListSimpleDto.builder()
                                .ruleId(rule.getRuleId())
                                .ruleOnOff(rule.getRuleOnOff())
                                .build();
    }
}
