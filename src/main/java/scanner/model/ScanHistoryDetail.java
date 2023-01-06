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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.dto.ScanResultDto;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCAN_HISTORY_DETAIL")
public class ScanHistoryDetail {
    
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
    private String resource;

    @Column(name = "RESOURCE_NAME")
    @NotNull
    private String resourceName;

    @Column(name = "SCAN_RESULT")
    @NotNull
    private String scanResult;

    @Column(name = "TARGET_FILE")
    @NotNull
    private String targetFile;

    @Column(name = "LINE")
    @NotNull
    private String line;

    @Column(name = "CODE")
    @NotNull
    private String code;

    public static ScanHistoryDetail toEntity(final ScanResultDto dto, CustomRule rule, ScanHistory history){
        return ScanHistoryDetail.builder()
                                .ruleSeq(rule)
                                .historySeq(history)
                                .resource(dto.getTarget_resource())
                                .resourceName(dto.getTarget_resource())
                                .scanResult(dto.getStatus())
                                .targetFile(dto.getTarget_file())
                                .line(dto.getLines())
                                .code(dto.getDetail())
                                .build();
    }
}
