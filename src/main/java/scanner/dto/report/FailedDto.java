package scanner.dto.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
