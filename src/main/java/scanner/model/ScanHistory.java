package scanner.model;

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
import javax.validation.constraints.Size;

import lombok.*;

import scanner.common.enums.Env;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SCAN_HISTORY")
public class ScanHistory extends BaseEntity {
    
    @Id
    @Column(name = "HISTORY_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historySeq;

    @OneToMany(mappedBy = "historySeq")
    private List<ScanHistoryDetail> details = new ArrayList<>();

    @Column(name = "FILE_NAME")
    @NotNull
    private String fileName;

    @Column(name = "FILE_HASH", updatable=false)
    @NotNull
    private String fileHash;

    @Column(name = "CSP")
    @NotNull
    @Size(max = 16)
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


    @Builder
    public ScanHistory(Long historySeq,
                       String fileName,
                       String fileHash,
                       String csp,
                       Integer passed,
                       Integer skipped,
                       Integer failed,
                       Integer high,
                       Integer medium,
                       Integer low,
                       Integer unknown,
                       Double score
    ) {
        this.historySeq = historySeq;
        this.fileName = fileName;
        this.fileHash = fileHash;
        this.csp = csp;
        this.passed = passed;
        this.skipped = skipped;
        this.failed = failed;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.unknown = unknown;
        this.score = score;
    }

    public static ScanHistory toEntity(
        String[] args,
        Integer passed,
        Integer skipped,
        Integer failed,
        double[] total,
        String provider
    ){
        return ScanHistory.builder()
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
