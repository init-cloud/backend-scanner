package scanner.scan.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.common.exception.ApiException;
import scanner.checklist.dto.CheckListDetailDto;
import scanner.common.enums.Env;
import scanner.checklist.entity.CustomRule;
import scanner.history.entity.ScanHistory;
import scanner.history.entity.ScanHistoryDetail;
import scanner.checklist.repository.CheckListRepository;
import scanner.history.repository.ScanHistoryDetailsRepository;
import scanner.history.repository.ScanHistoryRepository;
import scanner.scan.dto.ScanDto;
import scanner.common.enums.ResponseCode;
import scanner.scan.service.constants.ScanConstants;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanService {
	private final ScanHistoryRepository scanHistoryRepository;
	private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;
	private final CheckListRepository checkListRepository;

	@Transactional
	public ScanDto.Response scanTerraform(String[] args, String provider) {
		try {
			List<CustomRule> offRules = getOffedCheckList();
			String offStr = getSkipCheckCmd(offRules);
			String fileUploadPath = Env.UPLOAD_PATH.getValue();
			File file = new File(fileUploadPath + File.separator + args[1]);

			ProcessBuilder processBuilder = new ProcessBuilder("bash", "-l", "-c",
				Env.SHELL_COMMAND_RAW.getValue() + args[1] + Env.getCSPExternalPath(provider) + offStr);
			processBuilder.redirectErrorStream(true);
			processBuilder.directory(new File(fileUploadPath));
			Process process = processBuilder.start();

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
				ScanDto.Response scanResult = resultToJson(reader);

				int exitCode = process.waitFor();
				if (exitCode != 0) {
					throw new ApiException(ResponseCode.SCAN_ERROR);
				}

				// FileUtils.deleteDirectory(file);

				return scanResult;
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_LOAD_FILE_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
		}
	}

	public void saveScanDetails(ScanDto.Response scanResult, String[] args, String provider, double[] total) {

		ScanHistory scan = ScanHistory.toEntity(args, scanResult.getCheck().getPassed(),
			scanResult.getCheck().getSkipped(), scanResult.getCheck().getFailed(), total, provider,
			scanResult.getParse().toString());

		scan = scanHistoryRepository.save(scan);

		List<ScanHistoryDetail> details = new ArrayList<>();

		for (ScanDto.Result detail : scanResult.getResult()) {
			CustomRule saveRule = checkListRepository.findByRuleId(detail.getRuleId()).orElse(null);

			if (saveRule == null || saveRule.getId() == null)
				continue;

			details.add(ScanHistoryDetail.toEntity(detail, saveRule, scan));
		}
		scanHistoryDetailsRepository.saveAll(details);
	}

	public ScanDto.Check parseScanCheck(String scan) {
		String[] lines = scan.strip().split(", ");
		String[] passed = lines[0].split(ScanConstants.CHECK);
		String[] failed = lines[1].split(ScanConstants.CHECK);
		String[] skipped = lines[2].split(ScanConstants.CHECK);

		return new ScanDto.Check(Integer.parseInt(passed[1].strip()), Integer.parseInt(failed[1].strip()),
			Integer.parseInt(skipped[1].strip()));
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

	public ScanDto.Response resultToJson(BufferedReader br) throws IOException {
		StringBuilder sb = new StringBuilder();

		List<ScanDto.Result> resultLists = new ArrayList<>();
		ScanDto.Check check = new ScanDto.Check();
		ScanDto.Result result = new ScanDto.Result();

		Map<String, String> rulesMap = getCheckListDetailsList().stream()
			.collect(Collectors.toMap(CheckListDetailDto.Detail::getRuleId, CheckListDetailDto.Detail::getLevel));

		String rawResult;
		while ((rawResult = br.readLine()) != null) {
			if (rawResult.contains("Passed checks")) {
				check = parseScanCheck(rawResult);
			} else {

				result = parseScanResult(rawResult, result, rulesMap);

				if (result.getTargetFile() != null) {
					if (result.getStatus().equals(ScanConstants.PASSED)) {
						resultLists.add(result);
						result = new ScanDto.Result();
						sb = new StringBuilder();
					} else {
						sb.append(rawResult);
						sb.append("\n");

						if (rawResult.contains(result.getLines().split("-")[1] + " |")) {
							result.setDetail(sb.toString());
							resultLists.add(result);
							result = new ScanDto.Result();
							sb = new StringBuilder();
						}
					}
				}
			}
		}

		return new ScanDto.Response(check, resultLists);
	}

	private String getSkipCheckCmd(List<CustomRule> offRules) {
		if (offRules.isEmpty())
			return "";

		StringBuilder offStr = new StringBuilder();
		offStr.append(" --skip-check ");
		Arrays.stream(offRules.toArray()).filter(rule -> rule != null).forEach(rule -> {
			offStr.append(((CustomRule)rule).getRuleId()).append(",");
		});

		return offStr.substring(0, offStr.length() - 1);
	}

	public void saveScanHistory(ScanDto.Response result, String[] args, String provider) {
		double[] totalCount = calc(result.getResult());
		saveScanDetails(result, args, provider, totalCount);
	}

	public List<CustomRule> getOffedCheckList() {
		return checkListRepository.findByRuleOnOff("n");
	}

	@Transactional
	public List<CheckListDetailDto.Detail> getCheckListDetailsList() {
		List<CustomRule> ruleList = checkListRepository.findAll();
		return ruleList.stream().map(CheckListDetailDto.Detail::new).collect(Collectors.toList());
	}

	public double[] calc(List<ScanDto.Result> results) {
		/* score, high, medium, low, unknown */
		double[] count = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
		int totalHigh = 0, totalMedium = 0, totalLow = 0;

		for (ScanDto.Result result : results) {
			try {
				switch (result.getLevel()) {
					case "High":
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count[1]++;
						}
						totalHigh++;
						break;
					case "Medium":
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count[2]++;
						}
						totalMedium++;
						break;
					case "Low":
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count[3]++;
						}
						totalLow++;
						break;
					default:
						if (result.getStatus().equals(ScanConstants.PASSED)) {
							count[4]++;
						}
						totalLow++;
						break;
				}
			} catch (NullPointerException e) {
				// If result.getLevel() returns null
				if (result.getStatus().equals(ScanConstants.PASSED)) {
					count[3]++;
					totalLow++;
				}
			}
		}

		double total = calculateTotal(totalHigh, totalMedium, totalLow);
		if (total > 0.0) {
			double numerator = calculateNumerator(count);
			double score = (numerator / total) * 100.0;
			count[0] = Math.round(score * 10.0) / 10.0;
		}

		return count;
	}

	private double calculateTotal(int totalHigh, int totalMedium, int totalLow) {
		return totalHigh * 3.0 + totalMedium * 2.0 + totalLow * 1.0;
	}

	private double calculateNumerator(double[] count) {
		return 3.0 * count[1] + 2.0 * count[2] + count[3];
	}
}