package scanner.prototype.dto.history.report;

import java.util.LinkedHashMap;
import java.util.List;

import lombok.experimental.SuperBuilder;
import scanner.prototype.model.ScanHistoryDetail;
import scanner.prototype.model.Tag;

@SuperBuilder
public class FailedThreatDto extends FailedDto {
    
    public static LinkedHashMap<String, Integer> toMapDto(List<ScanHistoryDetail> details){

        LinkedHashMap<String, Integer> tag = new LinkedHashMap<>();

        if(details.isEmpty())
            return tag;

        details.stream()
                .forEach(
                    detail -> {
                        List<Tag> tags = detail.getRuleSeq().getTag();

                        for(int i = 0 ; i < tags.size() ; i++){
                            String key = tags.get(i).getTag();

                            if(tag.containsKey(key))
                                tag.put(key, tag.get(key) + 1);
                            else
                                tag.put(key, 1);
                        }
                    }
                );

        return tag;
    }
}
