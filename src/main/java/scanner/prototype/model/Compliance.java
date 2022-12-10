package scanner.prototype.model;


import java.time.LocalDateTime;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMPLIANCE")
public class Compliance {

    @Id
    @Column(name = "COMP_ID", updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long compId;

    @ManyToOne
    @JoinColumn(name = "rule_seq", updatable=false)
    private CustomRule ruleSeq;

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

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

    @Column(name = "DECRIPTION")
    private String description;

    @Column(name = "DETAIL")
    private String detail;
}