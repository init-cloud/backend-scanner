package scanner.dto.report;

import java.time.LocalDateTime;

import lombok.*;
import scanner.model.ScanHistory;


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

    public static ScanReportDto toDto(final ScanHistory entity) {
        return ScanReportDto.builder()
                .createdAt(entity.getCreatedAt())
                .modifiedAt(entity.getCreatedAt())
                .fileName(entity.getFileName())
                .fileHash(entity.getFileHash())
                .csp(entity.getCsp())
                .score(entity.getScore())
                .passed(entity.getPassed())
                .skipped(entity.getSkipped())
                .failed(entity.getFailed())
                .build();
    }

    public static ScanHistory toEntity(final ScanReportDto dto){
        return ScanHistory.builder()
            .fileName(dto.getFileName())
            .fileHash(dto.getFileHash())
            .csp(dto.getCsp())
            .score(dto.getScore())
            .passed(dto.getPassed())
            .skipped(dto.getSkipped())
            .failed(dto.getFailed())
            .build();
    }
}
