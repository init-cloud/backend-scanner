package scanner.dto.history;

import java.time.LocalDateTime;

import lombok.*;
import scanner.model.ScanHistory;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryDto {

    private Long id;
    private String fileName;
    private String fileHash;
    private LocalDateTime scanDateTime;
    private Double score;

    public HistoryDto(ScanHistory entity){
        this.id = entity.getHistorySeq();
        this.fileName = entity.getFileName();
        this.fileHash = entity.getFileHash();
        this.scanDateTime = entity.getCreatedAt();
        this.score = entity.getScore();
    }
}