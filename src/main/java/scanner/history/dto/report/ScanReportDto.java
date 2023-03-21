package scanner.history.dto.report;

import java.time.LocalDateTime;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanReportDto {
	private Long id;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	private String fileName;
	private String fileHash;
	private String csp;
	private Double score;
	private Integer passed;
	private Integer skipped;
	private Integer failed;
}
