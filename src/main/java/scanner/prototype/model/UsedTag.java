package scanner.prototype.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="used_tag")
public class UsedTag implements Serializable{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tagID;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_seq")
    private CustomRule ruleSeq;
}
