package scanner.checklist.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import scanner.common.entity.BaseEntity;
import scanner.scan.enums.Provider;
import scanner.checklist.dto.TagDto;
import scanner.history.entity.ScanHistoryDetail;

/*
	@Written by @Floodnut, v0.3.1-beta
	@Todo
	Columns moved to the CustomRuleDetails table will be deleted.
 */

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CUSTOM_RULE")
public class CustomRule extends BaseEntity {
	@Id
	@Column(name = "RULE_id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DEFAULT_RULE_NAME", updatable = false, unique = true, length = 64)
	@NotNull
	private String defaultRuleName;

	@Column(name = "PROVIDER")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Provider provider;

	@Column(name = "RULE_TYPE")
	@NotNull
	@Size(max = 16)
	private String ruleType;

	@Column(name = "SEVERITY")
	@NotNull
	private String level;

	@Column(name = "IS_MODIFIABLE")
	@NotNull
	private Character isModifiable;

	@Column(name = "INSECURE_EXAMPLE")
	private String insecureExample;

	@Column(name = "SECURE_EXAMPLE")
	private String secureExample;

	@Column(name = "CODE")
	private String code;

	@Column(name = "CUSTOM_DEFAULT", updatable = false)
	private String customDefault;

	@OneToMany(mappedBy = "rule")
	private List<CustomRuleDetails> ruleDetails = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<Tag> tags = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<ComplianceEng> complianceEngs = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<ComplianceKor> complianceKors = new ArrayList<>();

	@OneToMany(mappedBy = "rule")
	private List<ScanHistoryDetail> historyDetails = new ArrayList<>();

	public List<TagDto> getTagDto() {
		return tags.stream()
			.map(TagDto::new)
			.collect(Collectors.toList());
	}

	@Builder(builderClassName = "customRuleAddBuilder", builderMethodName = "customRuleAddBuilder")
	public CustomRule(String defaultRuleName, Provider provider, String ruleType, String level, Character isModifiable,
		String insecureExample, String secureExample, String code, String customDefault) {

		this.defaultRuleName = defaultRuleName;
		this.provider = provider;
		this.ruleType = ruleType;
		this.level = level;
		this.insecureExample = insecureExample;
		this.secureExample = secureExample;
		this.code = code;
		this.isModifiable = isModifiable;
		this.customDefault = customDefault;
	}

	/* For Test */
	public CustomRule(String defaultRuleName, Provider provider, List<ComplianceKor> complianceKors,
		List<ComplianceEng> complianceEngs, List<ScanHistoryDetail> historyDetails) {
		this.defaultRuleName = defaultRuleName;
		this.provider = provider;
		this.complianceEngs = complianceEngs;
		this.complianceKors = complianceKors;
		this.historyDetails = historyDetails;
	}

	public void addComplianceKorsForTest(List<ComplianceKor> kors) {
		this.complianceKors = kors;
	}

	public void addComplianceEngsForTest(List<ComplianceEng> engs) {
		this.complianceEngs = engs;
	}

	public void addHistoryDetailsForTest(List<ScanHistoryDetail> details) {
		this.historyDetails = details;
	}
}

