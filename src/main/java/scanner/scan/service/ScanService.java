package scanner.scan.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import scanner.checklist.dto.CheckListDetailDto;
import scanner.checklist.entity.UsedRule;
import scanner.checklist.repository.UsedCheckListRepository;
import scanner.common.enums.Env;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiAuthException;
import scanner.common.exception.ApiException;
import scanner.history.entity.ScanHistory;
import scanner.history.entity.ScanHistoryDetail;
import scanner.history.repository.ScanHistoryDetailsRepository;
import scanner.history.repository.ScanHistoryRepository;
import scanner.scan.dto.ScanDto;
import scanner.scan.service.constants.ScanConstants;
import scanner.security.provider.JwtTokenProvider;
import scanner.user.entity.User;
import scanner.user.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanService {
	private final ScanHistoryRepository scanHistoryRepository;
	private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;
	private final UsedCheckListRepository usedCheckListRepository;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public static class Score {

		@Getter
		double score;
		double high;
		double medium;
		double low;
		double unknown;

		protected Score() {
			this.score = 0.0;
			this.high = 0.0;
			this.medium = 0.0;
			this.low = 0.0;
			this.unknown = 0.0;
		}

		public int getHigh() {
			return (int)high;
		}

		public int getMedium() {
			return (int)medium;
		}

		public int getLow() {
			return (int)low;
		}

		public int getUnknown() {
			return (int)unknown;
		}
	}

	@Transactional
	public ScanDto.Response scanTerraformFiles(String[] args, String provider) {
		try {
			List<UsedRule> offRules = getOffedCheckList();
			String skipCheckedRules = getSkipCheckedRules(offRules);
			String fileUploadPath = Env.UPLOAD_PATH.getValue();
			File file = new File(fileUploadPath + File.separator + args[1]);

			ProcessBuilder processBuilder = new ProcessBuilder("bash", "-l", "-c",
				Env.SHELL_COMMAND_RAW.getValue() + args[1] + Env.getCSPExternalPath(provider) + skipCheckedRules);

			processBuilder.redirectErrorStream(true);
			processBuilder.directory(new File(fileUploadPath));
			Process process = processBuilder.start();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				ScanDto.Response scanResult = changeScanOutputToDto(reader);

				int exitCode = process.waitFor();
				log.info("Scan exitCode is {}", exitCode);
				FileUtils.deleteDirectory(file);

				return scanResult;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_LOAD_FILE_ERROR);
		} catch (Exception e) {
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	public void saveTerraformScanDetails(ScanDto.Response scanResult, String[] args, String provider, Score total) {
		User currentUser = getCurrentUser();
		ScanHistory scan = ScanHistory.toEntity(args, currentUser, scanResult.getCheck().getPassed(),
			scanResult.getCheck().getSkipped(), scanResult.getCheck().getFailed(), total, provider,
			scanResult.getParse().toString());

		scan = scanHistoryRepository.save(scan);

		List<ScanHistoryDetail> details = new ArrayList<>();

		for (ScanDto.Result detail : scanResult.getResult()) {
			UsedRule saveRule = usedCheckListRepository.findByUserAndRuleName(currentUser, detail.getRuleId())
				.orElse(null);

			if (saveRule == null || saveRule.getId() == null)
				continue;

			details.add(ScanHistoryDetail.toEntity(detail, saveRule, scan));
		}

		scanHistoryDetailsRepository.saveAll(details);
	}

	public ScanDto.Result parseScanResult(String rawResult, ScanDto.Result result, Map<String, String> rulesMap) {
		String[] lines = rawResult.split(ScanConstants.SPLIT_COLON_BLANK);

		if (rawResult.contains(ScanConstants.STATUS_CHECK)) {
			result.setRuleId(lines[1].strip());
			result.setDescription(lines[2].strip());
			result.setLevel(rulesMap.get(lines[1].strip()));
		} else if (rawResult.contains(ScanConstants.STATUS_PASSED)) {
			result.setStatus(ScanConstants.PASSED);
			result.setDetail("No");
			result.setTargetResource(lines[1].strip());
		} else if (rawResult.contains(ScanConstants.STATUS_FAILED)) {
			result.setStatus(ScanConstants.FAILED);
			result.setTargetResource(lines[1].strip());
		} else if (rawResult.contains(ScanConstants.STATUS_FILE)) {
			lines = rawResult.split(ScanConstants.SPLIT_COLON);
			result.setTargetFile(lines[1].strip());
			result.setLines(lines[2].strip());
		}

		return result;
	}

	public ScanDto.Response changeScanOutputToDto(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();

		List<ScanDto.Result> resultLists = new ArrayList<>();
		ScanDto.Check check = new ScanDto.Check();
		ScanDto.Result result = new ScanDto.Result();

		Map<String, String> rulesMap = getCheckListDetailsList().stream()
			.collect(Collectors.toMap(CheckListDetailDto.Detail::getRuleName, CheckListDetailDto.Detail::getLevel));

		String rawResult;
		while ((rawResult = br.readLine()) != null) {
			if (rawResult.contains("Passed checks")) {
				check = ScanDto.Check.parseScanCheck(rawResult);
				continue;
			}

			result = parseScanResult(rawResult, result, rulesMap);
			if (result.getTargetFile() == null)
				continue;

			if (result.getStatus().equals(ScanConstants.PASSED)) {
				resultLists.add(result);
				result = new ScanDto.Result();
				sb = new StringBuilder();
			} else if (rawResult.contains(result.getLines().split("-")[1] + " |")) {
				sb.append(rawResult).append("\n");
				result.setDetail(sb.toString());
				resultLists.add(result);
				result = new ScanDto.Result();
				sb = new StringBuilder();
			} else {
				sb.append(rawResult).append("\n");
			}
		}

		return new ScanDto.Response(check, resultLists);
	}

	private String getSkipCheckedRules(List<UsedRule> offRules) {
		if (offRules.isEmpty())
			return "";

		StringBuilder offStr = new StringBuilder();
		offStr.append(" --skip-check ");

		Arrays.stream(offRules.toArray())
			.filter(rule -> rule != null)
			.forEach(
				rule -> {
					offStr.append(((UsedRule)rule).getRuleName()).append(",");
				}
			);

		return offStr.substring(0, offStr.length() - 1);
	}

	public void saveScanHistory(ScanDto.Response result, String[] args, String provider) {
		Score totalCount = calcIacScore(result.getResult());
		saveTerraformScanDetails(result, args, provider, totalCount);
	}

	public List<UsedRule> getOffedCheckList() {
		User currentUser = getCurrentUser();
		return usedCheckListRepository.findByUserAndIsOn(currentUser, "n");
	}

	public List<CheckListDetailDto.Detail> getCheckListDetailsList() {
		List<UsedRule> ruleList = usedCheckListRepository.findAll();

		return ruleList.stream().map(CheckListDetailDto.Detail::new).collect(Collectors.toList());
	}

	public Score calcIacScore(List<ScanDto.Result> results) {
		Score count = new Score();
		int totalHigh = 0, totalMedium = 0, totalLow = 0;

		for (ScanDto.Result result : results) {
			try {
				switch (result.getLevel()) {
					case "High":
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count.high++;
						}
						totalHigh++;
						break;
					case "Medium":
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count.medium++;
						}
						totalMedium++;
						break;
					case "Low":
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count.low++;
						}
						totalLow++;
						break;
					default:
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count.unknown++;
						}
						totalLow++;
						break;
				}
			} catch (NullPointerException e) {
				// If result.getLevel() returns null
				if (result.getStatus().equals(ScanConstants.PASSED)) {
					count.low++;
					totalLow++;
				}
			}
		}

		double total = calculateTotal(totalHigh, totalMedium, totalLow);
		if (total > 0.0) {
			double numerator = calculateNumerator(count);
			double score = (numerator / total) * 100.0;
			count.score = Math.round(score * 10.0) / 10.0;
		}

		return count;
	}

	private double calculateTotal(int totalHigh, int totalMedium, int totalLow) {
		return totalHigh * 3.0 + totalMedium * 2.0 + totalLow * 1.0;
	}

	private double calculateNumerator(Score count) {
		return 3.0 * count.high + 2.0 * count.medium + count.low;
	}

	private User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiAuthException(ResponseCode.INVALID_USER));
	}
}