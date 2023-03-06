package scanner.model.rule;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.model.BaseEntity;
import scanner.model.enums.Language;

@Getter
@Entity
@Table(name = "CUSTOM_RULE_DETAILS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomRuleDetails extends BaseEntity {

	@Id
	@Column(name = "CUSTOM_RULE_DETAILS_ID", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ruleDetailsId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RULE_SEQ")
	private CustomRule ruleSeq;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "LANGUAGE")
	private Language language;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "EXPLANATION")
	private String explanation;

	@Column(name = "POSSIBLE_IMPACT")
	private String possibleImpact;

	@Column(name = "SOLUTION")
	private String sol;
}
