package scanner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import scanner.common.enums.Env;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCAN_HISTORY")
public class ScanHistory {
    
    @Id
    @Column(name = "HISTORY_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historySeq;

    @Column(name = "CREATED_AT", updatable=false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

    @Builder.Default
    @OneToMany(mappedBy = "historySeq")
    private List<ScanHistoryDetail> details = new ArrayList<ScanHistoryDetail>();

    @Column(name = "FILE_NAME")
    @NotNull
    private String fileName;

    @Column(name = "FILE_HASH", updatable=false)
    @NotNull
    private String fileHash;

    @Column(name = "CSP")
    @NotNull
    private String csp;

    @Column(name = "PASSED")
    @NotNull
    private Integer passed;

    @Column(name = "SKIPPED")
    @NotNull
    private Integer skipped;

    @Column(name = "FAILED")
    @NotNull
    private Integer failed;

    @Column(name = "HIGH")
    @NotNull
    private Integer high;

    @Column(name = "MEDIUM")
    @NotNull
    private Integer medium;

    @Column(name = "LOW")
    @NotNull
    private Integer low;

    @Column(name = "UNKNOWN")
    @NotNull
    private Integer unknown;

    @Column(name = "SCORE")
    @NotNull
    private Double score;

    public static ScanHistory toEntity(
        String[] args,
        String csp,
        Integer passed,
        Integer skipped,
        Integer failed,
        double[] total,
        String provider
    ){
        return ScanHistory.builder()
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .passed(passed)
                        .skipped(skipped)
                        .failed(failed)
                        .fileName(args[2])
                        .fileHash(args[0])
                        .score(total[0])
                        .high((int)total[1])
                        .medium((int)total[2])
                        .low((int)total[3])
                        .unknown((int)total[4])
                        .csp(Env.getCSP(provider))
                        .build();
    }
}
