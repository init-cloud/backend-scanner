package scanner.checklist.dto;

import java.util.List;

import lombok.*;

import scanner.checklist.entity.CustomRule;
import scanner.checklist.entity.CustomRuleDetails;
import scanner.checklist.entity.UsedRule;
import scanner.checklist.enums.SecurityType;
import scanner.common.enums.Language;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListDetailDto {

	@Getter
	public static class Detail {
		private String ruleName;
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
		private Character isModifiable;
		private Character isModified;

		@Builder(builderClassName = "checklistDetailsBuilder", builderMethodName = "checklistDetailsBuilder")
		public Detail(String ruleName, List<TagDto> tags, List<SecurityType> type, String level, String description,
			String explanation, String possibleImpact, String insecureExample, String secureExample, Solution solution,
			String state, String customDetail, Character isModifiable, Character isModified) {
			this.ruleName = ruleName;
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

		public Detail(UsedRule usedRule) {
			CustomRule rule = usedRule.getOriginRule();
			CustomRuleDetails details = rule.getRuleDetails()
				.stream()
				.filter(d -> d.getLanguage().equals(Language.ENGLISH))
				.findAny()
				.orElse(rule.getRuleDetails().get(0));

			this.ruleName = usedRule.getRuleName();
			this.state = usedRule.getIsOn().toString();
			this.isModified = usedRule.getIsModified();
			this.customDetail = usedRule.getCustomDetail();

			this.tags = rule.getTagDto();
			this.type = null;
			this.level = rule.getLevel();
			this.description = details.getDescription();
			this.explanation = details.getExplanation();
			this.possibleImpact = details.getPossibleImpact();
			this.insecureExample = rule.getInsecureExample();
			this.secureExample = rule.getSecureExample();
			this.solution = new Solution(details.getSol(), rule.getCode());
			this.isModifiable = rule.getIsModifiable();
		}
	}

	@Getter
	@AllArgsConstructor
	public static class Solution {
		private String sol;
		private String code;
	}

	public static Detail toDetailsDto(final UsedRule usedRule, final CustomRuleDetails details) {
		CustomRule rule = usedRule.getOriginRule();
		return Detail.checklistDetailsBuilder()

			.ruleName(usedRule.getRuleName())
			.state(usedRule.getIsOn().toString())
			.isModified(usedRule.getIsModified())
			.customDetail(usedRule.getCustomDetail())

			.tags(rule.getTagDto())
			.level(rule.getLevel())
			.description(details.getDescription())
			.explanation(details.getExplanation())
			.possibleImpact(details.getPossibleImpact())
			.insecureExample(rule.getInsecureExample())
			.secureExample(rule.getSecureExample())
			.solution(new Solution(details.getSol(), rule.getCode()))
			.isModifiable(rule.getIsModifiable())
			.build();
	}
}

