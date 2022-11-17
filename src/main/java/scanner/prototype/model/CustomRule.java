package scanner.prototype.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.model.enums.Provider;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CUSTOM_RULE")
public class CustomRule {
    @Id
    @Column(name = "RULE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "RULE_ID", unique = true)
    @NotNull
    @Size(max = 16)
    private String ruleId;

    @Column(name = "DEFAULT_RULE_ID")
    @NotNull
    @Size(max = 16)
    private String defaultRuleId;

    @Column(name = "RULE_ONOFF")
    @Size(max = 1)
    private String ruleOnOff;

    @Column(name = "PROVIDER", length = 8)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Provider provider;

    @Column(name = "RULE_TYPE", length = 16)
    @NotNull
    private String ruleType;

    @Column(name = "SEVERITY")
    @NotNull
    private String level;

    @Column(name = "CUSTOM_DETAIL")
    private String customDetail;

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

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
}
