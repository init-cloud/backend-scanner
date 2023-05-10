package scanner.history.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

import scanner.common.enums.Env;
import scanner.common.entity.BaseEntity;
import scanner.scan.service.ScanService;
import scanner.user.entity.User;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "SCAN_HISTORY")
public class ScanHistory extends BaseEntity {

	@Id
	@Column(name = "HISTORY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "FILE_NAME")
	@NotNull
	private String fileName;

	@Column(name = "FILE_HASH", updatable = false)
	@NotNull
	private String fileHash;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;

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

	@Column(name = "VISUAL")
	@NotNull
	private String visual;

	@OneToMany(mappedBy = "history")
	private List<ScanHistoryDetail> details = new ArrayList<>();

	@Builder(builderClassName = "addScanHistoryBuilder", builderMethodName = "addScanHistoryBuilder")
	public ScanHistory(Long historyId, User user, String fileName, String fileHash, String csp, Integer passed, Integer skipped,
		Integer failed, Integer high, Integer medium, Integer low, Integer unknown, Double score, String visual) {
		this.id = historyId;
		this.user = user;
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
		this.visual = visual;
	}

	public static ScanHistory toEntity(String[] args, User user, Integer passed, Integer skipped, Integer failed, ScanService.Score total,
		String provider, String visual) {
		return ScanHistory.addScanHistoryBuilder()
			.user(user)
			.passed(passed)
			.skipped(skipped)
			.failed(failed)
			.fileName(args[2])
			.fileHash(args[0])
			.score(total.getScore())
			.high(total.getHigh())
			.medium(total.getMedium())
			.low(total.getLow())
			.unknown(total.getUnknown())
			.csp(Env.getCSP(provider))
			.visual(visual)
			.build();
	}
}
