package scanner.model;


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
@Table(name = "TAG")
public class Tag {

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

    @Column(name = "CREATED_AT")
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;
}
