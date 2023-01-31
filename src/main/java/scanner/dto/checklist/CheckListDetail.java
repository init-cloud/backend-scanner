package scanner.dto.checklist;


import java.util.List;

import lombok.*;

import scanner.dto.TagDto;
import scanner.model.CustomRule;
import scanner.model.enums.SecurityType;

public class CheckListDetail {

    @Getter
    List<CheckListDetail.Detail> docs;

    public CheckListDetail(List<CheckListDetail.Detail> dto){
        this.docs = dto;
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class Detail{
        private String ruleId;
        private List<TagDto> tags;
        private List<SecurityType> type;
        private String level;
        private String description;
        private String explanation;
        private String possibleImpact;
        private String insecureExample;
        private String secureExample;
        private Solution solution;
        private String state;
        private String customDetail;
        private String isModifiable;
        private String isModified;

        @Builder
        public Detail(
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
            this.solution = new Solution(rule.getSol(), rule.getCode());
            this.state = rule.getRuleOnOff();
            this.isModifiable = rule.getIsModifiable();
            this.isModified = rule.getIsModified();
            this.customDetail = rule.getCustomDetail();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Solution{
        private String sol;
        private String code;
    }

    public static Detail toDto(CustomRule rule) {
        return Detail.builder()
                .ruleId(rule.getRuleId())
                .tags(rule.getTagDto())
                .level(rule.getLevel())
                .description(rule.getDescription())
                .explanation(rule.getExplanation())
                .possibleImpact(rule.getPossibleImpact())
                .insecureExample(rule.getInsecureExample())
                .secureExample(rule.getSecureExample())
                .solution(new Solution(rule.getSol(), rule.getCode()))
                .state(rule.getRuleOnOff())
                .isModifiable(rule.getIsModifiable())
                .isModified(rule.getIsModified())
                .customDetail(rule.getCustomDetail())
                .build();
    }

    public static CustomRule toEntity(final Detail dto){
        return CustomRule.builder()
                .ruleId(dto.getRuleId())
                .ruleOnOff(dto.getState())
                .isModified("y")
                .customDetail(dto.getCustomDetail())
                .build();
    }
}
