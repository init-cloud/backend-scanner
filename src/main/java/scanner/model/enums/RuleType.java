package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RuleType {
    TERRAFORM("Terraform");

    private final String ruleType;

    public static RuleType of(String ruleType) {

        return Arrays.stream(RuleType.values())
                .filter(r -> r.getRuleType().equals(ruleType))
                .findAny()
                .orElse(TERRAFORM);
    }
}