package scanner.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.dto.ScanResultDto;
import scanner.exception.ApiException;
import scanner.dto.CheckListDetailDto;
import scanner.common.enums.Env;
import scanner.model.CustomRule;
import scanner.model.ScanHistory;
import scanner.model.ScanHistoryDetail;
import scanner.repository.CheckListRepository;
import scanner.repository.ScanHistoryDetailsRepository;
import scanner.repository.ScanHistoryRepository;
import scanner.dto.CheckResultDto;
import scanner.dto.ParseResultDto;
import scanner.response.ScanResponse;
import scanner.common.utils.ParserRequest;
import scanner.response.enums.ResponseCode;


@Service
@RequiredArgsConstructor
public class ScanService {
    private final ParserRequest parserReq;
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

    private static final String SPLITCOLON = ": ";

    @Transactional
    public ScanResponse scanTerraform(String[] args, String provider) {
        try {
            List<CustomRule> offRules = checkListService.retrieveOffEntity();
            String offStr = getSkipCheckCmd(offRules);
            String fileUploadPath = Env.UPLOAD_PATH.getValue();
            File file = new File(fileUploadPath + File.separator + args[1]);

            String[] cmd = {"bash", "-l", "-c", Env.SHELL_COMMAND_RAW.getValue() + args[1] + Env.getCSPExternalPath(provider) + offStr};

            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ScanResponse scanResult = resultToJson(br, args[1], provider);

            p.waitFor();
            p.destroy();
            double[] totalCount = calc(scanResult.getResult());

            save(scanResult.getCheck(), scanResult.getResult(), args, provider, totalCount);
            FileUtils.deleteDirectory(file);

            return scanResult;
        } catch (NullPointerException e){
            throw new ApiException(ResponseCode.STATUS_4005);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e){
            throw new ApiException(ResponseCode.STATUS_5006);
        } catch (ParseException e){
            throw new ApiException(ResponseCode.STATUS_5007);
        }
        return null;
    }

    @Transactional
    public boolean save(CheckResultDto scanResult, List<ScanResultDto> scanDetail, String[] args, String provider, double[] total)
    {
        try {
            ScanHistory scan = ScanHistory.toEntity(args, provider, scanResult.getPassed(), scanResult.getSkipped(), scanResult.getFailed(), total, provider);

            scan = scanHistoryRepository.save(scan);

            List<ScanHistoryDetail> details = new ArrayList<>();

            for(int i = 0 ; i < scanDetail.size() ; i++){
                CustomRule saveRule = checkListRepository.findByRuleId(scanDetail.get(i).getRuleId());

                if(saveRule == null || saveRule.getId() == null)
                    continue;
                    
                details.add(ScanHistoryDetail.toEntity(scanDetail.get(i), saveRule, scan));
            }
            scanHistoryDetailsRepository.saveAll(details);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CheckResultDto parseScanCheck(String scan){
        String[] lines = scan.strip().split(", ");
        String[] passed = lines[0].split(CHECK);
        String[] failed = lines[1].split(CHECK);
        String[] skipped = lines[2].split(CHECK);

        return new CheckResultDto(
            Integer.parseInt(passed[1].strip()), 
            Integer.parseInt(failed[1].strip()), 
            Integer.parseInt(skipped[1].strip())
        );
    }

    public ScanResultDto parseScanResult(
        String rawResult,
        ScanResultDto result,
        Map<String, String> rulesMap
    ){
        String[] lines;

        if(rawResult.contains(STATUSCHECK)){
            lines = rawResult.split(SPLITCOLON);

            result.setRuleId(lines[1].strip());
            result.setDescription(lines[2].strip());
            result.setLevel(rulesMap.get(lines[1].strip()));
        }
        
        if(rawResult.contains(STATUSPASSED)){
            lines = rawResult.split(SPLITCOLON);

            result.setStatus(PASSED);
            result.setDetail("No");
            result.setTargetResource(lines[1].strip());
        }
        else if(rawResult.contains(STATUSFAILED)){
            lines = rawResult.split(SPLITCOLON);

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

    public ScanResponse resultToJson(BufferedReader br, String path, String provider)
    throws IOException, ParseException
    {
        String rawResult;
        StringBuilder sb = new StringBuilder();
        CheckResultDto check = new CheckResultDto();
        ScanResultDto result = new ScanResultDto();
        List<ScanResultDto> resultLists = new ArrayList<>();
        ParseResultDto parse = new ParseResultDto(parserReq.getTerraformParsingData(path, provider));
        List<CheckListDetailDto> rulesInfo = checkListService.retrieve().getDocs();
        Map<String, String> rulesMap = new HashMap<>();
        
        for(int i = 0 ; i < rulesInfo.size(); i++)
            rulesMap.put(rulesInfo.get(i).getId(), rulesInfo.get(i).getLevel());

        while((rawResult = br.readLine()) != null){
            if(rawResult.contains("Passed checks")){
                check = parseScanCheck(rawResult);
                continue;
            }

            result = parseScanResult(rawResult, result, rulesMap);

            if(result.getTargetFile() != null){
                if(result.getStatus().equals(PASSED)){
                    resultLists.add(result);
                    result = new ScanResultDto();
                    sb = new StringBuilder();
                }
                else{
                    sb.append(rawResult);
                    sb.append("\n");

                    if(rawResult.contains(result.getLines().split("-")[1] + " |")){
                        result.setDetail(sb.toString());
                        resultLists.add(result);
                        result = new ScanResultDto();
                        sb = new StringBuilder();
                    }
                }
            } 
        }

        return new ScanResponse(check, resultLists, parse);
    }

    private String getSkipCheckCmd(List<CustomRule> offRules){
        StringBuilder offStr = new StringBuilder();
        offStr.append("");

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

    private double[] calc(List<ScanResultDto> results){
        /* score, high, medium, low, unknown */
        double[] count = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
        int success = 0;
        int total = 0;
        int severity = 0;
        int totalSeverity = 0;

        for(ScanResultDto result: results){

            if(result == null)
                continue;

            total += 1;
            switch(result.getLevel()){
                case "High":
                    if(result.getStatus().equals(PASSED)){
                        success += 1;
                        severity += 3;
                        count[1] += 1;
                    }
                    totalSeverity += 3;
                    break;
                case "Medium":
                    if(result.getStatus().equals(PASSED)){
                        success += 1;
                        severity += 2;
                        count[2] += 1;
                    }
                    totalSeverity += 2;
                    break;
                case "Low":
                    if(result.getStatus().equals(PASSED)){
                        success += 1;
                        severity += 1;
                        count[3] += 1;
                    }
                    totalSeverity += 1;
                    break;
                default:
                    if(result.getStatus().equals(PASSED)){
                        success += 1;
                        severity += 1;
                        count[4] += 1;
                    }
                    totalSeverity += 1;
                    break;
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