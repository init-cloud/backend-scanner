package scanner.dto.history;

import lombok.*;
import scanner.model.ScanHistory;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryDto {
    private Long id;
    private LocalDateTime scanDateTime;
    private Double score;


    public HistoryDto(ScanHistory entity){
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
