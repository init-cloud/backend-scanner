package scanner.checklist.dto;

import java.util.List;

import lombok.*;

import scanner.checklist.entity.CustomRule;
import scanner.checklist.entity.CustomRuleDetails;
import scanner.checklist.enums.SecurityType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListDetailDto {

	@AllArgsConstructor
	@Builder
	@Getter
	public static class Detail {
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
		private Character isModifiable;
		private Character isModified;

		@Builder
		public Detail(CustomRule rule) {
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
	public static class Solution {
		private String sol;
		private String code;
	}

	public static Detail toDto(CustomRule rule, CustomRuleDetails details) {
		return Detail.builder()
			.ruleId(rule.getRuleId())
			.tags(rule.getTagDto())
			.level(rule.getLevel())
			.description(details.getDescription())
			.explanation(details.getExplanation())
			.possibleImpact(details.getPossibleImpact())
			.insecureExample(rule.getInsecureExample())
			.secureExample(rule.getSecureExample())
			.solution(new Solution(details.getSol(), rule.getCode()))
			.state(rule.getRuleOnOff())
			.isModifiable(rule.getIsModifiable())
			.isModified(rule.getIsModified())
			.customDetail(rule.getCustomDetail())
			.build();
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

	public static CustomRule toEntity(final Detail dto) {
		return CustomRule.builder()
			.ruleId(dto.getRuleId())
			.ruleOnOff(dto.getState())
			.isModified('y')
			.customDetail(dto.getCustomDetail())
			.build();
	}
}

