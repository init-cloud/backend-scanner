package scanner.dto.rule;

import lombok.*;
import scanner.model.rule.CustomRule;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListSimpleDto {

	@Getter
	public static class Response {
		List<CheckListSimpleDto.Simple> data;

		@Builder
		public Response(List<CheckListSimpleDto.Simple> data) {
			this.data = data;
		}
	}

	@AllArgsConstructor
	@Builder
	@Getter
	public static class Simple {
		private String ruleId;
		private String ruleOnOff;
		private String level;
		private Custom custom;

		public Simple(final CustomRule rule) {
			this.ruleId = rule.getRuleId();
			this.ruleOnOff = rule.getRuleOnOff();
			this.level = rule.getLevel();
			this.custom = new Custom(rule.getCustomDetail());
		}
	}

	@AllArgsConstructor
	@Getter
	public static class Custom {
		private String customDetail;
	}

	public static Simple toDto(CustomRule rule) {
		return Simple.builder()
			.ruleId(rule.getRuleId())
			.ruleOnOff(rule.getRuleOnOff())
			.custom(new Custom(rule.getCustomDetail()))
			.build();
	}

	public static CustomRule toEntity(final CheckListSimpleDto.Simple dto) {
		if (dto.getCustom() == null)
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
