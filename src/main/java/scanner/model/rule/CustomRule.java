package scanner.model.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import scanner.model.BaseEntity;
import scanner.model.user.UsedRule;
import scanner.model.enums.Provider;
import scanner.dto.rule.TagDto;
import scanner.model.history.ScanHistoryDetail;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CUSTOM_RULE")
public class CustomRule extends BaseEntity {
	@Id
	@Column(name = "RULE_SEQ", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "RULE_ID", unique = true)
	@NotNull
	@Size(max = 16)
	private String ruleId;

	@Setter
	@OneToMany(mappedBy = "ruleSeq")
	private List<Tag> tags = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "ruleSeq")
	private List<ComplianceEng> complianceEngs = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "ruleSeq")
	private List<ComplianceKor> complianceKors = new ArrayList<>();

	@Setter
	@OneToMany(mappedBy = "ruleSeq")
	private List<ScanHistoryDetail> historyDetails = new ArrayList<>();

	@Column(name = "DEFAULT_RULE_ID", updatable = false)
	@NotNull
	@Size(max = 16)
	private String defaultRuleId;

	@Column(name = "RULE_ONOFF")
	@Size(max = 1)
	private String ruleOnOff;

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

	@Column(name = "IS_MODIFIED")
	@NotNull
	private Character isModified;

	@Column(name = "IS_MODIFIABLE")
	@NotNull
	private Character isModifiable;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EXPLANATION")
	private String explanation;

	@Column(name = "POSSIBLE_IMPACT")
	private String possibleImpact;

	@Column(name = "INSECURE_EXAMPLE")
	private String insecureExample;

	@Column(name = "SECURE_EXAMPLE")
	private String secureExample;

	@Column(name = "SOLUTION")
	private String sol;

	@Column(name = "CODE")
	private String code;

	@Column(name = "CUSTOM_DETAIL")
	private String customDetail;

	@Column(name = "CUSTOM_DEFAULT", updatable = false)
	private String customDefault;

	@OneToMany(mappedBy = "customRule")
	private List<UsedRule> usedRules = new ArrayList<>();

	@Builder
	public CustomRule(String ruleId, String defaultRuleId, String ruleOnOff, Provider provider, String ruleType,
		String level, Character isModified, Character isModifiable, String description, String explanation,
		String possibleImpact, String insecureExample, String secureExample, String sol, String code,
		String customDetail, String customDefault, List<UsedRule> usedRules) {

		this.ruleId = ruleId;
		this.defaultRuleId = defaultRuleId;
		this.ruleOnOff = ruleOnOff;
		this.provider = provider;
		this.ruleType = ruleType;
		this.level = level;
		this.customDetail = customDetail;
		this.description = description;
		this.explanation = explanation;
		this.possibleImpact = possibleImpact;
		this.insecureExample = insecureExample;
		this.secureExample = secureExample;
		this.sol = sol;
		this.code = code;
		this.isModified = isModified;
		this.isModifiable = isModifiable;
		this.customDefault = customDefault;
		this.usedRules = usedRules;
	}

	public CustomRule(String ruleId, String defaultRuleId, Provider provider, List<ComplianceKor> complianceKors,
		List<ComplianceEng> complianceEngs, List<ScanHistoryDetail> historyDetails) {
		this.ruleId = ruleId;
		this.defaultRuleId = defaultRuleId;
		this.provider = provider;
		this.complianceEngs = complianceEngs;
		this.complianceKors = complianceKors;
		this.historyDetails = historyDetails;
	}

	public List<TagDto> getTagDto() {
		return tags.stream().map(TagDto::new).collect(Collectors.toList());
	}
}
