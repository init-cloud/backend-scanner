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

import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import scanner.checklist.service.CheckListService;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScanService {

	private final CheckListService checkListService;
	private final ScanHistoryRepository scanHistoryRepository;
	private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;
	private final CheckListRepository checkListRepository;

	private static final String CHECK = "checks:";
	private static final String PASSED = "passed";
	private static final String FAILED = "failed";
	private static final String STATUS_CHECK = "Check";
	private static final String STATUS_PASSED = "PASSED";
	private static final String STATUS_FAILED = "FAILED";
	private static final String STATUS_FILE = "File";
	private static final String SPLIT_COLON_BLANK = ": ";
	private static final String SPLIT_COLON = ":";

	@Transactional
	public ScanDto.Response scanTerraform(String[] args, String provider) {
		try {
			List<CustomRule> offRules = checkListService.getOffedCheckList();
			String offStr = getSkipCheckCmd(offRules);
			String fileUploadPath = Env.UPLOAD_PATH.getValue();
			File file = new File(fileUploadPath + File.separator + args[1]);

			String[] cmd = {"bash", "-l", "-c",
				Env.SHELL_COMMAND_RAW.getValue() + args[1] + Env.getCSPExternalPath(provider) + offStr};

			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			ScanDto.Response scanResult = resultToJson(br);

			p.waitFor();
			p.destroy();

			FileUtils.deleteDirectory(file);

			return scanResult;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return null;
		} catch (IOException e) {
			throw new ApiException(ResponseCode.SERVER_LOAD_FILE_ERROR);
		} catch (Exception e) {
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
		String[] passed = lines[0].split(CHECK);
		String[] failed = lines[1].split(CHECK);
		String[] skipped = lines[2].split(CHECK);

		return new ScanDto.Check(Integer.parseInt(passed[1].strip()), Integer.parseInt(failed[1].strip()),
			Integer.parseInt(skipped[1].strip()));
	}

	public ScanDto.Result parseScanResult(String rawResult, ScanDto.Result result, Map<String, String> rulesMap) {
		String[] lines;

		if (rawResult.contains(STATUS_CHECK)) {
			lines = rawResult.split(SPLIT_COLON_BLANK);

			result.setRuleId(lines[1].strip());
			result.setDescription(lines[2].strip());
			result.setLevel(rulesMap.get(lines[1].strip()));
		}

		if (rawResult.contains(STATUS_PASSED)) {
			lines = rawResult.split(SPLIT_COLON_BLANK);

			result.setStatus(PASSED);
			result.setDetail("No");
			result.setTargetResource(lines[1].strip());
		} else if (rawResult.contains(STATUS_FAILED)) {
			lines = rawResult.split(SPLIT_COLON_BLANK);

			result.setStatus(FAILED);
			result.setTargetResource(lines[1].strip());
		}

		if (rawResult.contains(STATUS_FILE)) {
			lines = rawResult.split(SPLIT_COLON);

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

		Map<String, String> rulesMap = checkListService.getCheckListDetailsList()
			.stream()
			.collect(Collectors.toMap(CheckListDetailDto.Detail::getRuleId, CheckListDetailDto.Detail::getLevel));

		String rawResult;
		while ((rawResult = br.readLine()) != null) {
			if (rawResult.contains("Passed checks")) {
				check = parseScanCheck(rawResult);
				continue;
			}

			result = parseScanResult(rawResult, result, rulesMap);

			if (result.getTargetFile() != null) {
				if (result.getStatus().equals(PASSED)) {
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

		return new ScanDto.Response(check, resultLists);
	}

	private String getSkipCheckCmd(List<CustomRule> offRules) {
		StringBuilder offStr = new StringBuilder();

		if (!offRules.isEmpty()) {
			offStr.append(" --skip-check ");

			Arrays.stream(offRules.toArray()).filter(rule -> rule != null).forEach(rule -> {
				offStr.append(((CustomRule)rule).getRuleId());
				offStr.append(",");
			});
		}
		String result = offStr.toString();
		return result.substring(0, result.length() - 1);
	}

	public double[] calc(List<ScanDto.Result> results) {
		/* score, high, medium, low, unknown */
		double[] count = new double[] {0.0, 0.0, 0.0, 0.0, 0.0};
		int totalHigh = 0;
		int totalMedium = 0;
		int totalLow = 0;

		for (ScanDto.Result result : results) {
			try {
				switch (result.getLevel()) {
					case "High":
						if (result.getStatus().equals(PASSED)) {
							count[1] += 1;
						}
						totalHigh += 1;
						break;
					case "Medium":
						if (result.getStatus().equals(PASSED)) {
							count[2] += 1;
						}
						totalMedium += 1;
						break;
					case "Low":
						if (result.getStatus().equals(PASSED)) {
							count[3] += 1;
						}
						totalLow += 1;
						break;
					default:
						if (result.getStatus().equals(PASSED)) {
							count[4] += 1;
						}
						totalLow += 1;
						break;
				}
			} catch (NullPointerException e) {
				if (result.getStatus().equals(PASSED)) {
					count[3] += 1;
					totalLow += 1;
				}
			}
		}

		double down = totalHigh * 3.0 + totalMedium * 2.0 + totalLow * 1.0;

		return getScore(down, count);
	}

	private double[] getScore(double down, double[] count) {

		if (down == 0.0) {
			count[0] = 0.0;
		} else {
			double up = (3 * count[1] + 2 * count[2] * 1 * count[3] + 1 * count[4]);
			count[0] = Math.round((up * 100.0) / down) * 10.0 / 10.0;
		}

		return count;
	}
}