package scanner.dto;


import java.util.List;

import lombok.*;

import scanner.model.CustomRule;
import scanner.model.enums.SecurityType;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListDetailDto {
    private String ruleId;
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


    @Builder
    public CheckListDetailDto(
            String ruleId,
            List<TagDto> tags,
            List<SecurityType> type,
            String level,
            String description,
            String explanation,
            String possibleImpact,
            String insecureExample,
            String secureExample,
            SolutionDto solution,
            String state,
            String customDetail,
            String isModifiable,
            String isModified
    ) {
        this.ruleId = ruleId;
        this.tags = tags;
        this.type = type;
        this.level = level;
        this.description = description;
        this.explanation = explanation;
        this.possibleImpact = possibleImpact;
        this.insecureExample = insecureExample;
        this.secureExample = secureExample;
        this.solution = solution;
        this.state = state;
        this.customDetail = customDetail;
        this.isModifiable = isModifiable;
        this.isModified = isModified;
    }


    @Builder
    public CheckListDetailDto(
        CustomRule rule
    ){
        this.ruleId = rule.getRuleId();
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
                            .ruleId(rule.getRuleId())
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
                .ruleId(dto.getRuleId())
                .ruleOnOff(dto.getState())
                .isModified("y")
                .customDetail(dto.getCustomDetail())
                .build();
    }
}
