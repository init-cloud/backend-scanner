package scanner.prototype.dto.history;

import scanner.prototype.model.ScanHistory;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private Long id;
    private LocalDateTime scanDateTime;
    private Double score;


    public HistoryDto(ScanHistory entity){
        this.id = entity.getHistorySeq();
        this.scanDateTime = entity.getCreatedAt(); 
        this.score = entity.getScore();
    }

    public HistoryDto toDto(ScanHistory entity){
        return HistoryDto.builder()
                        .id(entity.getHistorySeq())
                        .scanDateTime(entity.getCreatedAt())
                        .score(entity.getScore())
                        .build();
    }
}
