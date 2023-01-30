package scanner.dto.report;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.SuperBuilder;
import scanner.model.ScanHistoryDetail;

@SuperBuilder
public class FailedResourceDto extends FailedDto{

    public static Map<String, Integer> toMapDto(List<ScanHistoryDetail> details){

        Map<String, Integer> resource = new LinkedHashMap<>();

        if(details.isEmpty())
            return resource;

        details.stream().forEach(
            detail -> {
                String key = detail.getResource();
                if(resource.containsKey(detail.getResource())) resource.put(key, resource.get(key) + 1);
                else resource.put(key, 1);
                }
            );

        return resource;
    }
}