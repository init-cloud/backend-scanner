package scanner.history.dto.report;

import lombok.*;
import scanner.checklist.entity.Compliance;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComplianceDto {
	private String compliance;
	private String complianceNumber;
	private String description;

	public static ComplianceDto toDto(final Compliance entity) {

		return ComplianceDto.builder()
			.compliance(entity.getComplianceName())
			.complianceNumber(entity.getComplianceNumber())
			.description(entity.getDescription())
			.build();
	}
}
