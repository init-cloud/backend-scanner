package scanner.prototype.dto.history.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import scanner.prototype.model.ScanHistoryDetail;

@Data
public class FailedResourceDto {

    public static Map<String, Integer> toDto(List<ScanHistoryDetail> details){

        Map<String, Integer> resource = new HashMap<>();

        for(int i = 0 ; i < details.size() ; i++){
            String key = details.get(i).getResource();
            if(resource.containsKey(details.get(i).getResource()))
                resource.put(key, resource.get(key) + 1);
            else
                resource.put(key, 1);
        }

        return resource;
    }
}
