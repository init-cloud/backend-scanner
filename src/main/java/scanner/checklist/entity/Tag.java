package scanner.checklist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import scanner.common.entity.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TAG")
public class Tag extends BaseEntity {

	@Id
	@Column(name = "TAG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "rule_seq")
	private CustomRule ruleSeq;

	@Column(name = "TAG")
	@NotNull
	@Size(max = 64)
	private String tagName;

	@Builder(builderClassName = "tagFromRuleBuilder", builderMethodName = "tagFromRuleBuilder")
	public Tag(CustomRule ruleSeq, String tagName) {
		this.ruleSeq = ruleSeq;
		this.tagName = tagName;
	}
}
