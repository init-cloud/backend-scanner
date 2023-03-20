package scanner.history.dto.report;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.*;
import scanner.checklist.entity.CustomRule;
import scanner.checklist.entity.CustomRuleDetails;
import scanner.checklist.entity.Tag;
import scanner.common.enums.Language;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;
import scanner.history.entity.ScanHistoryDetail;

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

		Object details = Arrays.stream(rule.getRuleDetails().toArray())
			.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.ENGLISH)
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

		Supplier<Stream<Object>> streamSupplier = () -> Arrays.stream(rule.getRuleDetails().toArray());
		Object details = streamSupplier.get()
			.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.KOREAN)
			.findFirst()
			.orElse(streamSupplier.get()
				.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.ENGLISH)
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
