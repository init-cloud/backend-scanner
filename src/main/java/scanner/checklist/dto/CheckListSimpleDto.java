package scanner.checklist.dto;

import lombok.*;
import scanner.checklist.entity.CustomRule;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListSimpleDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Response {
		List<CheckListSimpleDto.Simple> data;

		public Response(List<CheckListSimpleDto.Simple> data) {
			this.data = data;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Simple {
		private String ruleId;
		private String ruleOnOff;
		private String level;
		private String custom;

		public Simple(final CustomRule rule) {
			this.ruleId = rule.getRuleId();
			this.ruleOnOff = rule.getRuleOnOff();
			this.level = rule.getLevel();
			this.custom = rule.getCustomDetail();
		}
	}
}
