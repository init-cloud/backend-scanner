package scanner.scan.dto;

import java.util.List;

import lombok.*;
import scanner.scan.service.constants.ScanConstants;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ScanDto {

	@Getter
	public static class Response {
		private Check check;
		private List<Result> result;
		private Object parse;

		public Response(Check check, List<Result> result) {
			this.check = check;
			this.result = result;
			this.parse = null;
		}

		public void addVisualizingResult(Object parse) {
			this.parse = parse;
		}
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Check {
		private int passed;
		private int failed;
		private int skipped;

		public static ScanDto.Check parseScanCheck(String scan) {
			String[] lines = scan.strip().split(", ");
			return ScanDto.Check.toCheckDto(lines);
		}

		public static Check toCheckDto(String[] lines) {
			int passedCount = parseCount(lines[0]);
			int failedCount = parseCount(lines[1]);
			int skippedCount = parseCount(lines[2]);

			return new Check(passedCount, failedCount, skippedCount);
		}

		private static int parseCount(String line) {
			return Integer.parseInt(line.split(ScanConstants.CHECK)[1].strip());
		}
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