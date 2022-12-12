package scanner.prototype.dto.history.report;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.dto.TagDto;
import scanner.prototype.model.Compliance;
import scanner.prototype.model.CustomRule;
import scanner.prototype.model.ScanHistoryDetail;
import scanner.prototype.model.Tag;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanHistoryDetailDto {

    private String ruleID;
    private String result;
    private String severity;
    private String description;
    private List<String> type;
    private String fileName;
    private String line;
    private String resource;
    private String resourceName;
    private String problematicCode;
    private String possibleImpact;
    private String solutionSample;
    private String solution;
    private List<ComplianceDto> compliance;

    public static ScanHistoryDetailDto toDto(final ScanHistoryDetail entity){
        CustomRule rule = entity.getRuleSeq();
        List<ComplianceDto> compliance = null;

        List<String> tag = rule.getTag()
                                .stream()
                                .map(Tag::getTag)
                                .collect(Collectors.toList()); 

        return ScanHistoryDetailDto.builder()
                                    .ruleID(rule.getRuleId())
                                    .result(entity.getScanResult())
                                    .severity(rule.getLevel())
                                    .type(tag)
                                    .fileName(entity.getTargetFile())
                                    .line(entity.getLine())
                                    .resource(entity.getResource())
                                    .resourceName(entity.getResourceName())
                                    .problematicCode(entity.getCode())
                                    .possibleImpact(rule.getPossibleImpact())
                                    .solutionSample(rule.getCode())
                                    .solution(rule.getSol())
                                    .compliance(compliance)
                                    .build();
    }

    public ScanHistoryDetailDto(ScanHistoryDetail entity){
        CustomRule rule = entity.getRuleSeq();

        this.ruleID = rule.getRuleId();
        this.result = entity.getScanResult();
        this.severity = rule.getLevel();
        this.description = rule.getDescription();
        this.type = null;
        this.fileName = entity.getTargetFile();
        this.line = entity.getLine();
        this.resource = entity.getResource();
        this.resourceName = entity.getResourceName();
        this.problematicCode = entity.getCode();
        this.compliance = null;
    }
}
