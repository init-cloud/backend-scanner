package scanner.dto.report;

import lombok.*;
import scanner.model.rule.ComplianceEng;
import scanner.model.rule.ComplianceKor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComplianceDto {
	private String compliance;
	private String complianceNumber;
	private String description;

	public static ComplianceDto toDto(final ComplianceEng entity) {

		return ComplianceDto.builder()
			.compliance(entity.getComplianceName())
			.complianceNumber(entity.getComplianceNumber())
			.description(entity.getDescription())
			.build();
	}

	public static ComplianceDto toDto(final ComplianceKor entity) {

		return ComplianceDto.builder()
			.compliance(entity.getComplianceName())
			.complianceNumber(entity.getComplianceNumber())
			.description(entity.getDescription())
			.build();
	}
}
