package scanner.scan.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanDto {

	@Getter
	@AllArgsConstructor
	public static class Response {
		private final Check check;
		private final List<Result> result;
		private final Object parse;
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Check {
		private int passed;
		private int failed;
		private int skipped;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Result {
		private String status;
		private String ruleId;
		private String description;
		private String targetResource;
		private String targetFile;
		private String lines;
		private String level;
		private String detail;
	}
}