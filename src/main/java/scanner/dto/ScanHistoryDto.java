package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.model.ScanHistory;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
