package scanner.prototype.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCAN_HISTORY")
public class ScanHistory {
    
    @Id
    @Column(name = "HISTORY_SEQ", updatable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATED_AT", updatable=false)
    @NotNull
    private LocalDateTime createdAt;

    @Column(name = "MODIFIED_AT")
    @NotNull
    private LocalDateTime modifiedAt;

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

    @Column(name = "SCORE")
    @NotNull
    private Double score;
}
