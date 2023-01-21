package scanner.dto.history.report;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class FailedDto {
    private String name;
    private Integer count;

    public static List<FailedDto> mapToDtp(Map<String, Integer> map){
        List<FailedDto> dto = new ArrayList<>();
        map.forEach((key, value) -> dto.add(new FailedDto(key, value)));

        return dto;
    }
}
