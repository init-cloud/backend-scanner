package scanner.checklist.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListModifyDto {

	/**
	 * @ToDo Need to Modifying Camel Case with DB Update.
	 */
	@Getter
	@AllArgsConstructor
	public static class Modifying {
		private final String rule_id;
		private final List<Details> custom;
	}

	@Getter
	@AllArgsConstructor
	public static class Details {
		private final String name;
		private final String value;
	}

	@Getter
	@AllArgsConstructor
	public static class State {
		private final String rule_id;
		private final String ruleOnOff;
	}
}
