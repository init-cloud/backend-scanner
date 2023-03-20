package scanner.history.dto.report;

import java.util.List;
import java.util.stream.Collectors;

import lombok.*;
import scanner.checklist.entity.CustomRule;
import scanner.history.entity.ScanHistoryDetail;
import scanner.checklist.entity.Tag;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public static ScanHistoryDetailDto toDto(final ScanHistoryDetail entity) {
		CustomRule rule = entity.getRuleSeq();
		List<ComplianceDto> compliance = rule.getCompliance()
			.stream()
			.map(ComplianceDto::toDto)
			.collect(Collectors.toList());

		List<String> tag = rule.getTag().stream().map(Tag::getTagName).collect(Collectors.toList());

		return ScanHistoryDetailDto.builder()
			.ruleID(rule.getRuleId())
			.description(rule.getDescription())
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
}
