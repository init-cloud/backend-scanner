package scanner.dto.report;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.SuperBuilder;
import scanner.model.ScanHistoryDetail;
import scanner.model.Tag;

@SuperBuilder
public class FailedThreatDto extends FailedDto {
    
    public static Map<String, Integer> toMapDto(List<ScanHistoryDetail> details){

        Map<String, Integer> tag = new LinkedHashMap<>();

        if(details.isEmpty())
            return tag;

        details.stream().forEach(
            detail -> {
                List<Tag> tags = detail.getRuleSeq().getTag();

                for(int i = 0 ; i < tags.size() ; i++){
                    String key = tags.get(i).getTagName();

                    if(tag.containsKey(key)) tag.put(key, tag.get(key) + 1);
                    else tag.put(key, 1);
                }
            }
        );

        return tag;
    }
}
