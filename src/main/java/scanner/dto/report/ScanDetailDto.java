package scanner.dto.report;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import scanner.model.CustomRule;
import scanner.model.ScanHistoryDetail;


@Builder
@AllArgsConstructor
@Getter
public class ScanDetailDto {
    private String ruleID;
    private String severity;
    private String result;
    private String solution;
    private String controlName;
    private String description;
    private String type;
    private String fileName;
    private String line;
    private String resource;
    private String resourceName;
    private String problematicCode;
    private List<ComplianceDto> unfulfilledCompliance;

    public ScanDetailDto(ScanHistoryDetail entity){
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
        this.unfulfilledCompliance = rule.getCompliance().stream().map(ComplianceDto::toDto).collect(Collectors.toList());
    }
}
