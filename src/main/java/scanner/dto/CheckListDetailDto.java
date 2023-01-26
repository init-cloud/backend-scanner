package scanner.dto;


import java.util.List;

import lombok.*;

import scanner.model.CustomRule;
import scanner.model.enums.SecurityType;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListDetailDto {
    private Long seq;
    private String id;
    private List<TagDto> tags;
    private List<SecurityType> type;
    private String level;
    private String description;
    private String explanation;
    private String possibleImpact;
    private String insecureExample;
    private String secureExample;
    private SolutionDto solution; 
    private String state;
    private String customDetail;
    private String isModifiable;
    private String isModified;


    public CheckListDetailDto(
        CustomRule rule
    ){
        this.seq = rule.getId();
        this.id = rule.getRuleId();
        this.tags = rule.getTagDto();
        this.type = null;
        this.level = rule.getLevel();
        this.description = rule.getDescription();
        this.explanation = rule.getExplanation();
        this.possibleImpact = rule.getPossibleImpact();
        this.insecureExample = rule.getInsecureExample();
        this.secureExample = rule.getSecureExample();
        this.solution = new SolutionDto(rule.getSol(), rule.getCode()); 
        this.state = rule.getRuleOnOff();
        this.isModifiable = rule.getIsModifiable();
        this.isModified = rule.getIsModified();
        this.customDetail = rule.getCustomDetail();
    }

    public static CheckListDetailDto toDto(CustomRule rule) {
        return CheckListDetailDto.builder()
                            .seq(rule.getId())
                            .id(rule.getRuleId())
                            .tags(rule.getTagDto())
                            .level(rule.getLevel())
                            .description(rule.getDescription())
                            .explanation(rule.getExplanation())
                            .possibleImpact(rule.getPossibleImpact())
                            .insecureExample(rule.getInsecureExample())
                            .secureExample(rule.getSecureExample())
                            .solution(new SolutionDto(rule.getSol(), rule.getCode()))
                            .state(rule.getRuleOnOff())
                            .isModifiable(rule.getIsModifiable())
                            .isModified(rule.getIsModified())
                            .customDetail(rule.getCustomDetail())
                            .build();
    }

    public static CustomRule toEntity(final CheckListDetailDto dto){
        return CustomRule.builder()
                .ruleId(dto.getId())
                .ruleOnOff(dto.getState())
                .isModified("y")
                .customDetail(dto.getCustomDetail())
                .build();
    }
}
