package scanner.checklist.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListModifyDto {

	/**
	 * @ToDo Need to Modifying Camel Case with DB Update.
	 */
	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Modifying {
		private String rule_id;
		private List<Details> custom;

		public static String toJsonString(List<Details> dto) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				return objectMapper.writeValueAsString(dto);
			} catch (JsonProcessingException e) {
				throw new ApiException(ResponseCode.INVALID_REQUEST);
			}
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Details {
		private String name;
		private String value;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class State {
		private String rule_id;
		private String ruleOnOff;
	}
}

