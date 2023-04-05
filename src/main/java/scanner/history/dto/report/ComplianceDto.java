package scanner.history.dto.report;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.checklist.entity.ComplianceEng;
import scanner.checklist.entity.ComplianceKor;

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
