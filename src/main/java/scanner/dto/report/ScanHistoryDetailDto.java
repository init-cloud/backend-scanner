package scanner.dto.report;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.*;
import scanner.common.enums.ResponseCode;
import scanner.exception.ApiException;
import scanner.model.enums.Language;
import scanner.model.rule.CustomRule;
import scanner.model.history.ScanHistoryDetail;
import scanner.model.rule.CustomRuleDetails;
import scanner.model.rule.Tag;

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

	public static ScanHistoryDetailDto toEng(final ScanHistoryDetail entity) {
		CustomRule rule = entity.getRuleSeq();
		List<ComplianceDto> compliance = rule.getComplianceEngs()
			.stream()
			.map(ComplianceDto::toDto)
			.collect(Collectors.toList());

		List<String> tag = rule.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());

		Stream<Object> ruleStream = Arrays.stream(rule.getRuleDetails().toArray());
		Object details = ruleStream.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.ENGLISH)
			.findFirst()
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return ScanHistoryDetailDto.toDto(entity, rule, (CustomRuleDetails)details, tag, compliance);
	}

	public static ScanHistoryDetailDto toKor(final ScanHistoryDetail entity) {
		CustomRule rule = entity.getRuleSeq();
		List<ComplianceDto> compliance = rule.getComplianceKors()
			.stream()
			.map(ComplianceDto::toDto)
			.collect(Collectors.toList());

		List<String> tag = rule.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());

		Stream<Object> ruleStream = Arrays.stream(rule.getRuleDetails().toArray());
		Object details = ruleStream.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.KOREAN)
			.findFirst()
			.orElse(ruleStream.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.ENGLISH)
				.findFirst()
				.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST)));

		return ScanHistoryDetailDto.toDto(entity, rule, (CustomRuleDetails)details, tag, compliance);

	}

	public static ScanHistoryDetailDto toDto(final ScanHistoryDetail entity, final CustomRule rule,
		final CustomRuleDetails details, final List<String> tag, final List<ComplianceDto> compliance) {
		return ScanHistoryDetailDto.builder()
			.ruleID(rule.getRuleId())
			.description(details.getDescription())
			.result(entity.getScanResult())
			.severity(rule.getLevel())
			.type(tag)
			.fileName(entity.getTargetFile())
			.line(entity.getLine())
			.resource(entity.getResource())
			.resourceName(entity.getResourceName())
			.problematicCode(entity.getCode())
			.possibleImpact(details.getPossibleImpact())
			.solutionSample(rule.getCode())
			.solution(details.getSol())
			.compliance(compliance)
			.build();
	}
}
