package scanner.model;

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
import scanner.dto.scan.ScanDto;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "SCAN_HISTORY_DETAIL")
public class ScanHistoryDetail extends BaseEntity {
    
    @Id
    @Column(name = "ID", updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rule_seq", updatable=false)
    private CustomRule ruleSeq;

    @ManyToOne
    @JoinColumn(name = "history_seq", updatable=false)
    private ScanHistory historySeq;

    @Column(name = "RESOURCE")
    @NotNull
    @Size(max = 16)
    private String resource;

    @Column(name = "RESOURCE_NAME")
    @NotNull
    @Size(max = 32)
    private String resourceName;

    @Column(name = "SCAN_RESULT")
    @NotNull
    @Size(max = 8)
    private String scanResult;

    @Column(name = "TARGET_FILE")
    @NotNull
    private String targetFile;

    @Column(name = "LINE")
    @NotNull
    @Size(max = 8)
    private String line;

    @Column(name = "CODE")
    @NotNull
    private String code;


    @Builder
    public ScanHistoryDetail(CustomRule ruleSeq,
                             ScanHistory historySeq,
                             String resource,
                             String resourceName,
                             String scanResult,
                             String targetFile,
                             String line,
                             String code
    ) {
        this.ruleSeq = ruleSeq;
        this.historySeq = historySeq;
        this.resource = resource;
        this.resourceName = resourceName;
        this.scanResult = scanResult;
        this.targetFile = targetFile;
        this.line = line;
        this.code = code;
    }

    public static ScanHistoryDetail toEntity(final ScanDto.Result dto, CustomRule rule, ScanHistory history){
        return ScanHistoryDetail.builder()
                                .ruleSeq(rule)
                                .historySeq(history)
                                .resource(dto.getTargetResource())
                                .resourceName(dto.getTargetResource())
                                .scanResult(dto.getStatus())
                                .targetFile(dto.getTargetFile())
                                .line(dto.getLines())
                                .code(dto.getDetail())
                                .build();
    }
}
