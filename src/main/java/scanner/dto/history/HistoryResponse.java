package scanner.dto.history;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import scanner.model.ScanHistory;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoryResponse {
    List<History> history;

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class History {
        private Long id;
        private LocalDateTime scanDateTime;
        private Double score;

        public History(ScanHistory entity){
            this.id = entity.getHistorySeq();
            this.scanDateTime = entity.getCreatedAt();
            this.score = entity.getScore();
        }

        public History toDto(ScanHistory entity){
            return History.builder()
                    .id(entity.getHistorySeq())
                    .scanDateTime(entity.getCreatedAt())
                    .score(entity.getScore())
                    .build();
        }
    }
}
