package scanner.checklist.dto;

import lombok.*;
import scanner.checklist.entity.UsedRule;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Response {
		List<CheckListDto.Summary> data = new ArrayList<>();

		public Response(List<CheckListDto.Summary> data) {
			this.data = data;
		}
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Summary {
		private String ruleName;
		private String ruleOnOff;
		private String level;
		private String custom;

		public Summary(final UsedRule rule) {
			this.ruleName = rule.getRuleName();
			this.ruleOnOff = rule.getIsOn().toString();
			this.level = rule.getOriginRule().getLevel();
			this.custom = rule.getCustomDetail();
		}
	}

	/**
	 * @Todo Need to Modifying Camel Case with DB Update.
	 */
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Modifying {
		private String ruleName;
		private List<CheckListDto.Modifying.Details> custom;

		public static String toJsonString(List<CheckListDto.Modifying.Details> dto) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.writeValueAsString(dto);
			} catch (JsonProcessingException e) {
				throw new ApiException(ResponseCode.INVALID_REQUEST);
			}
		}

		@Getter
		@AllArgsConstructor
		@NoArgsConstructor(access = AccessLevel.PROTECTED)
		static class Details {
			private String name;
			private String value;
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class RuleState {
		private String ruleName;
		private String ruleOnOff;
	}
}
