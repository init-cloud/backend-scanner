package scanner.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.common.dto.HttpRequestUrlParam;
import scanner.common.utils.CommonHttpRequest;
import scanner.exception.ApiException;
import scanner.dto.checklist.CheckListDetail;
import scanner.common.enums.Env;
import scanner.model.CustomRule;
import scanner.model.ScanHistory;
import scanner.model.ScanHistoryDetail;
import scanner.repository.CheckListRepository;
import scanner.repository.ScanHistoryDetailsRepository;
import scanner.repository.ScanHistoryRepository;
import scanner.dto.scan.ScanDto;
import scanner.dto.enums.ResponseCode;


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
    private static final String STATUSCHECK = "Check";
    private static final String STATUSPASSED = "PASSED";
    private static final String STATUSFAILED= "FAILED";
    private static final String STATUSFILE = "File";
    private static final String SPLITCOLONBLANK = ": ";
    private static final String SPLITCOLON = ":";

    @Transactional
    public ScanDto.Response scanTerraform(String[] args, String provider) {
        try {
            List<CustomRule> offRules = checkListService.retrieveOffEntity();
            String offStr = getSkipCheckCmd(offRules);
            String fileUploadPath = Env.UPLOAD_PATH.getValue();
            File file = new File(fileUploadPath + File.separator + args[1]);

            String[] cmd = {"bash", "-l", "-c", Env.SHELL_COMMAND_RAW.getValue() + args[1] + Env.getCSPExternalPath(provider) + offStr};

            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ScanDto.Response scanResult = resultToJson(br, args[1], provider);

            p.waitFor();
            p.destroy();
            double[] totalCount = calc(scanResult.getResult());

            save(scanResult.getCheck(), scanResult.getResult(), args, provider, totalCount);
            FileUtils.deleteDirectory(file);

            return scanResult;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e){
            throw new ApiException(ResponseCode.STATUS_5006);
        }
        return null;
    }

    @Transactional
    public void save(ScanDto.Check scanResult,
                        List<ScanDto.Result> scanDetails,
                        String[] args, String provider,
                        double[] total
    ) {
        try {
            ScanHistory scan = ScanHistory.toEntity(args, scanResult.getPassed(), scanResult.getSkipped(), scanResult.getFailed(), total, provider);

            scan = scanHistoryRepository.save(scan);

            List<ScanHistoryDetail> details = new ArrayList<>();

            for(ScanDto.Result detail : scanDetails){
                CustomRule saveRule = checkListRepository.findByRuleId(detail.getRuleId())
                        .orElseThrow(() -> new ApiException(ResponseCode.STATUS_4007));

                if(saveRule == null || saveRule.getId() == null)
                    continue;
                    
                details.add(ScanHistoryDetail.toEntity(detail, saveRule, scan));
            }
            scanHistoryDetailsRepository.saveAll(details);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.STATUS_5003);
        }
    }

    public ScanDto.Check parseScanCheck(String scan){
        String[] lines = scan.strip().split(", ");
        String[] passed = lines[0].split(CHECK);
        String[] failed = lines[1].split(CHECK);
        String[] skipped = lines[2].split(CHECK);

        return new ScanDto.Check(
            Integer.parseInt(passed[1].strip()), 
            Integer.parseInt(failed[1].strip()), 
            Integer.parseInt(skipped[1].strip())
        );
    }

    public ScanDto.Result parseScanResult(
        String rawResult,
        ScanDto.Result result,
        Map<String, String> rulesMap
    ){
        String[] lines;

        if(rawResult.contains(STATUSCHECK)){
            lines = rawResult.split(SPLITCOLONBLANK);

            result.setRuleId(lines[1].strip());
            result.setDescription(lines[2].strip());
            result.setLevel(rulesMap.get(lines[1].strip()));
        }
        
        if(rawResult.contains(STATUSPASSED)){
            lines = rawResult.split(SPLITCOLONBLANK);

            result.setStatus(PASSED);
            result.setDetail("No");
            result.setTargetResource(lines[1].strip());
        }
        else if(rawResult.contains(STATUSFAILED)){
            lines = rawResult.split(SPLITCOLONBLANK);

            result.setStatus(FAILED);
            result.setTargetResource(lines[1].strip());
        }

        if(rawResult.contains(STATUSFILE)){
            lines = rawResult.split(SPLITCOLON);

            result.setTargetFile(lines[1].strip());
            result.setLines(lines[2].strip());
        }
        return result;
    }

    public ScanDto.Response resultToJson(BufferedReader br, String path, String provider)
    throws IOException
    {
        CommonHttpRequest commonHttpRequest = new CommonHttpRequest();

        StringBuilder sb = new StringBuilder();

        List<ScanDto.Result> resultLists = new ArrayList<>();
        ScanDto.Check check = new ScanDto.Check();
        ScanDto.Result result = new ScanDto.Result();

        HttpRequestUrlParam get = new HttpRequestUrlParam();
        get.setUrlParam(provider + "/" + path);
        Object parse = commonHttpRequest.HttpGetRequestBuffer(Env.PARSE_API.getValue(), get);

        Map<String, String> rulesMap = new HashMap<>();
        List<CheckListDetail.Detail> rulesInfo = checkListService.retrieve().getDocs();
        for(CheckListDetail.Detail info : rulesInfo)
            rulesMap.put(info.getRuleId(), info.getLevel());

        String rawResult;
        while((rawResult = br.readLine()) != null){
            if(rawResult.contains("Passed checks")){
                check = parseScanCheck(rawResult);
                continue;
            }

            result = parseScanResult(rawResult, result, rulesMap);

            if(result.getTargetFile() != null){
                if(result.getStatus().equals(PASSED)){
                    resultLists.add(result);
                    result = new ScanDto.Result();
                    sb = new StringBuilder();
                }
                else{
                    sb.append(rawResult);
                    sb.append("\n");

                    if(rawResult.contains(result.getLines().split("-")[1] + " |")){
                        result.setDetail(sb.toString());
                        resultLists.add(result);
                        result = new ScanDto.Result();
                        sb = new StringBuilder();
                    }
                }
            } 
        }

        return new ScanDto.Response(check, resultLists, parse);
    }

    private String getSkipCheckCmd(List<CustomRule> offRules){
        StringBuilder offStr = new StringBuilder();

        if(!offRules.isEmpty()){
            offStr.append(" --skip-check ");

            for(int i = 0 ; i < offRules.size() ; i++){
                if(offRules.get(i) == null)
                    continue;

                offStr.append(offRules.get(i).getRuleId());

                if(i + 1 < offRules.size())
                    offStr.append(",");
            }
        }
        return offStr.toString();
    }

    private double[] calc(List<ScanDto.Result> results){
        /* score, high, medium, low, unknown */
        double[] count = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        int success = 0;
        int total = 0;
        int severity = 0;
        int totalSeverity = 0;

        for(ScanDto.Result result: results){

            try {
                total += 1;
                switch (result.getLevel()) {
                    case "High":
                        if (result.getStatus().equals(PASSED)) {
                            success += 1;
                            severity += 3;
                            count[1] += 1;
                        }
                        totalSeverity += 3;
                        break;
                    case "Medium":
                        if (result.getStatus().equals(PASSED)) {
                            success += 1;
                            severity += 2;
                            count[2] += 1;
                        }
                        totalSeverity += 2;
                        break;
                    case "Low":
                        if (result.getStatus().equals(PASSED)) {
                            success += 1;
                            severity += 1;
                            count[3] += 1;
                        }
                        totalSeverity += 1;
                        break;
                    default:
                        if (result.getStatus().equals(PASSED)) {
                            success += 1;
                            severity += 1;
                            count[4] += 1;
                        }
                        totalSeverity += 1;
                        break;
                }
            }catch (NullPointerException e){
                continue;
            }
        }

        double down = (total * totalSeverity);

        return getScore(down, count, success, severity);
    }

    private double[] getScore(double down, double[] count, int success, int severity) {

        if(down == 0.0)
            count[0] = 0.0;
        else
            count[0] = Math.round((success * severity * 100.0)/down) * 10.0 / 10.0;

        return count;
    }
}