package scanner.dto.checklist;


import lombok.*;
import scanner.model.CustomRule;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListSimple {

    List<CheckListSimple> data;


    @AllArgsConstructor
    @Builder
    @Getter
    public static class Simple{
        private String ruleId;
        private String ruleOnOff;
        private Custom custom;

        public Simple(final CustomRule rule){
            this.ruleId = rule.getRuleId();
            this.ruleOnOff = rule.getRuleOnOff();
            this.custom = new Custom(rule.getCustomDetail());
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Custom{
        private String customDetail;
    }

    public static Simple toDto(CustomRule rule) {
        return Simple.builder()
                .ruleId(rule.getRuleId())
                .ruleOnOff(rule.getRuleOnOff())
                .custom(new Custom(rule.getCustomDetail()))
                .build();
    }

    public static CustomRule toEntity(final CheckListSimple.Simple dto){
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
