package scanner.prototype.dto.history.report;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import scanner.prototype.model.CustomRule;
import scanner.prototype.model.ScanHistoryDetail;


@Builder
@Data
@AllArgsConstructor
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
        this.unfulfilledCompliance = null;
    }
}
