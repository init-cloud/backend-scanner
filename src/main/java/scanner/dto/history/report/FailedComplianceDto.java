package scanner.dto.history.report;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.experimental.SuperBuilder;
import scanner.model.Compliance;
import scanner.model.ScanHistoryDetail;

@SuperBuilder
public class FailedComplianceDto extends FailedDto{
        
    public FailedComplianceDto(String name, Integer count) {
        super(name, count);
    }

    public static Map<String, Integer> toMapDto(List<ScanHistoryDetail> details){

        Map<String, Integer> compliance = new LinkedHashMap<>();

        if(details.isEmpty())
            return compliance;

        details.stream()
                .forEach(
                    detail -> {
                        List<Compliance> compliances = detail.getRuleSeq().getCompliance();

                        for(int i = 0 ; i < compliances.size() ; i++){
                            String key = compliances.get(i).getComplianceNumber();

                            if(compliance.containsKey(key))
                                compliance.put(key, compliance.get(key) + 1);
                            else
                                compliance.put(key, 1);
                        }
                    }
                );

        return compliance;
    }
}
