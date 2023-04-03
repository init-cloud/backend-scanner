package scanner.history.dto.history;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VisualDto {

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class Response {
		private Long historySeq;
		private LocalDateTime scanDate;
		private String result;

		public Response(Long historySeq, LocalDateTime scanDate, String result) {
			this.historySeq = historySeq;
			this.scanDate = scanDate;
			this.result = result;
		}
	}
}
