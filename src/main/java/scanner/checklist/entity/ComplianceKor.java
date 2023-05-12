package scanner.checklist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import scanner.common.entity.BaseEntity;

/*
	@Written by @Floodnut, v0.3.1-beta
	@Todo
	Tables separated by language(Korean, English) will be combined into one table.
	Details will be organized into separate tables.
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMPLIANCE_KOR")
public class ComplianceKor extends BaseEntity {

	@Id
	@Column(name = "COMP_ID", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long compId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rule_id", updatable = false)
	private CustomRule rule;

	@Column(name = "COMPLIANCE_NAME")
	@NotNull
	@Size(max = 16)
	private String complianceName;

	@Column(name = "COMPLIANCE_NUMBER")
	@NotNull
	@Size(max = 8)
	private String complianceNumber;

	@Column(name = "CATEGORY")
	@NotNull
	@Size(max = 128)
	private String category;

	@Column(name = "ARTICLE")
	@NotNull
	@Size(max = 128)
	private String article;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DETAIL")
	private String detail;

	@Builder(builderClassName = "complianceKoreanBuilder", builderMethodName = "complianceKoreanBuilder")
	public ComplianceKor(CustomRule rule, String complianceName, String complianceNumber, String category,
		String article, String description, String detail) {
		this.rule = rule;
		this.complianceName = complianceName;
		this.complianceNumber = complianceNumber;
		this.category = category;
		this.article = article;
		this.description = description;
		this.detail = detail;
	}
}