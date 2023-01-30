package scanner.dto.history;

import lombok.*;
import scanner.model.ScanHistory;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScanHistoryDto {

    private Long id;

    public static ScanHistoryDto toDto(final ScanHistory entity) {
        return ScanHistoryDto.builder()
                .id(entity.getHistorySeq())
                .build();
    }

    public static ScanHistory toEntity(final ScanHistoryDto dto){
        return ScanHistory.builder()
                .historySeq(dto.getId())
                .build();
    }
}
