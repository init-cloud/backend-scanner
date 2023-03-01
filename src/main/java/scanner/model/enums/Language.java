package scanner.model.enums;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Language {
	ENGLISH("eng"), KOREAN("kor");

	@Getter
	private final String lang;

	public static Language of(String code) {

		return Arrays.stream(Language.values()).filter(r -> r.getLang().equals(code)).findAny().orElse(ENGLISH);
	}
}
