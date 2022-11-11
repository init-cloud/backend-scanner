package scanner.prototype.dto;


import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.prototype.model.CustomRule;
import scanner.prototype.model.enums.SecurityType;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckListDetailDto {
    private Long seq;
    private String id;
    private List<TagDto> tags;
    private List<SecurityType> type;
    private String description;
    private String explanation;
    private String possibleImpact;
    private String insecureExample;
    private String secureExample;
    private SolutionDto solution; 
    private String state;

    public CheckListDetailDto(
        CustomRule rule
    ){
        this.seq = rule.getId();
        this.id = rule.getRuleId();
        this.tags = null;
        this.type = null;
        this.description = rule.getDescription();
        this.explanation = rule.getExplanation();
        this.possibleImpact = rule.getPossibleImpact();
        this.insecureExample = rule.getInsecureExample();
        this.secureExample = rule.getSecureExample();
        this.solution = new SolutionDto(rule.getSol(), rule.getCode()); 
        this.state = rule.getRuleOnOff();
    }

    public static CheckListDetailDto toDto(CustomRule rule) {
        return CheckListDetailDto.builder()
                            .seq(rule.getId())
                            .id(rule.getRuleId())
                            .description(rule.getDescription())
                            .explanation(rule.getExplanation())
                            .possibleImpact(rule.getPossibleImpact())
                            .insecureExample(rule.getInsecureExample())
                            .secureExample(rule.getSecureExample())
                            .solution(new SolutionDto(rule.getSol(), rule.getCode()))
                            .state(rule.getRuleOnOff())
                            .build();
    }

    public static CustomRule toEntity(final CheckListDetailDto dto){
        return CustomRule.builder()
                .id(dto.getSeq())
                .ruleId(dto.getId())
                .ruleOnOff(dto.getState())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
