package scanner.checklist.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import scanner.common.entity.BaseEntity;
import scanner.user.entity.UsedRule;
import scanner.scan.enums.Provider;
import scanner.checklist.dto.TagDto;
import scanner.history.entity.ScanHistoryDetail;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "compliance")
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

	@OneToMany(mappedBy = "ruleSeq", fetch = FetchType.LAZY)
	private final List<Tag> tag = new ArrayList<>();

	@OneToMany(mappedBy = "ruleSeq", fetch = FetchType.LAZY)
	private final List<Compliance> compliance = new ArrayList<>();

	@OneToMany(mappedBy = "ruleSeq", fetch = FetchType.LAZY)
	private final List<ScanHistoryDetail> historyDetails = new ArrayList<>();

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customRule")
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

	public List<TagDto> getTagDto() {
		return tag.stream().map(TagDto::new).collect(Collectors.toList());
	}
}
