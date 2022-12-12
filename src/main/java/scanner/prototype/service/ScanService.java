package scanner.prototype.service;

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
import scanner.prototype.dto.CheckListDetailDto;
import scanner.prototype.env.Env;
import scanner.prototype.exception.ScanException;
import scanner.prototype.model.CustomRule;
import scanner.prototype.model.ScanHistory;
import scanner.prototype.model.ScanHistoryDetail;
import scanner.prototype.repository.CheckListRepository;
import scanner.prototype.repository.ScanHistoryDetailsRepository;
import scanner.prototype.repository.ScanHistoryRepository;
import scanner.prototype.response.CheckResponse;
import scanner.prototype.response.ParseResponse;
import scanner.prototype.response.ResultResponse;
import scanner.prototype.response.ScanResponse;
import scanner.prototype.utils.ParserRequest;


@Service
@RequiredArgsConstructor
public class ScanService {
    private final ParserRequest parserReq;
    private final CheckListService checkListService;
    private final ScanHistoryRepository scanHistoryRepository;
    private final ScanHistoryDetailsRepository scanHistoryDetailsRepository;
    private final CheckListRepository checkListRepository;

    /**
     * 
     * @param args
     * @return
     * @throws Exception
     */
    public ScanResponse<?> scanTerraform(String[] args, String provider) 
    throws Exception 
    {
        try {
            List<CustomRule> offRules = checkListService.retrieveOffEntity();
            String offStr = getSkipCheckCmd(offRules);
            String fileUploadPath = Env.UPLOAD_PATH.getValue();
            File file = new File(fileUploadPath + File.separator + args[1]);
            
            String[] cmd = {"bash", "-l", "-c", Env.SHELL_COMMAND_RAW.getValue() + args[1] + File.separator + Env.getCSPExternalPath(provider) + offStr};
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ScanResponse<?> scanResult = resultToJson(br, args[1], provider);

            p.waitFor();
            p.destroy();
            double[] totalCount = calc(scanResult.getResult());

            save(scanResult.getCheck(), scanResult.getResult(), args, provider, totalCount);
            FileUtils.deleteDirectory(file);
            return scanResult;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScanException("Scan Error.");
        }
    }

    /**
     * 
     * @param scanResult
     * @param scanDetail
     * @param args
     * @param provider
     * @return
     */
    @Transactional
    public boolean save(CheckResponse scanResult, List<ResultResponse> scanDetail, String[] args, String provider, double[] total) 
    {
        try {
            ScanHistory scan = ScanHistory.toEntity(args, provider, scanResult.getPassed(), scanResult.getSkipped(), scanResult.getFailed(), total, provider);

            scan = scanHistoryRepository.save(scan);

            List<ScanHistoryDetail> details = new ArrayList<>();

            for(int i = 0 ; i < scanDetail.size() ; i++){
                CustomRule saveRule = checkListRepository.findByRuleId(scanDetail.get(i).getRule_id());

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


    /**
     * 
     * @param scan
     * @return
     */
    public CheckResponse parseScanCheck(String scan){
        String[] lines = scan.strip().split(", ");
        String[] passed = lines[0].split("checks:");
        String[] failed = lines[1].split("checks:");
        String[] skipped = lines[2].split("checks:");

        return new CheckResponse(
            Integer.parseInt(passed[1].strip()), 
            Integer.parseInt(failed[1].strip()), 
            Integer.parseInt(skipped[1].strip())
        );
    }

    /**
     * 
     * @param rawResult
     * @param result
     * @return
     */
    public ResultResponse parseScanResult(
        String rawResult,
        ResultResponse result,
        Map<String, String> rulesMap
    ){
        String[] lines;

        if(rawResult.contains("Check:")){
            lines = rawResult.split(": ");

            result.setRule_id(lines[1].strip());
            result.setDescription(lines[2].strip());
            result.setLevel(rulesMap.get(lines[1].strip()));
        }
        
        if(rawResult.contains("PASSED")){
            lines = rawResult.split(": ");

            result.setStatus("passed");
            result.setDetail("No");
            result.setTarget_resource(lines[1].strip());
        }
        else if(rawResult.contains("FAILED")){
            lines = rawResult.split(": ");

            result.setStatus("failed");
            result.setTarget_resource(lines[1].strip());
        }

        if(rawResult.contains("File")){
            lines = rawResult.split(":");

            result.setTarget_file(lines[1].strip());
            result.setLines(lines[2].strip());
        }

        return result;
    }

    /**
     * 
     * @param br
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public ScanResponse<?> resultToJson(BufferedReader br, String path, String provider) 
    throws IOException, ParseException, FileNotFoundException
    {
        String rawResult;
        StringBuilder sb = new StringBuilder();
        CheckResponse check = new CheckResponse();
        ResultResponse result = new ResultResponse();
        List<ResultResponse> resultLists = new ArrayList<>();
        ParseResponse parse = new ParseResponse(parserReq.getTerraformParsingData(path, provider)); 
        List<CheckListDetailDto> rulesInfo = checkListService.retrieve().getDocs();
        Map<String, String> rulesMap = new HashMap<String, String>(); 
        
        for(int i = 0 ; i < rulesInfo.size(); i++)
            rulesMap.put(rulesInfo.get(i).getId(), rulesInfo.get(i).getLevel());

        while((rawResult = br.readLine()) != null){
            if(rawResult.contains("Passed checks")){
                check = parseScanCheck(rawResult);
                continue;
            }

            result = parseScanResult(rawResult, result, rulesMap);

            if(result.getTarget_file() != null){
                if(result.getStatus() == "passed"){
                    resultLists.add(result);
                    result = new ResultResponse();
                    sb = new StringBuilder();
                }
                else{
                    sb.append(rawResult);
                    sb.append("\n");

                    if(rawResult.contains(result.getLines().split("-")[1] + " |")){
                        result.setDetail(sb.toString());
                        resultLists.add(result);
                        result = new ResultResponse();
                        sb = new StringBuilder();
                    }
                }
            } 
        }

        return new ScanResponse(check, resultLists, parse);
    }

    /**
     * get Skip rule List
     * @param offRules
     * @return offStr (String)
     */
    private String getSkipCheckCmd(List<CustomRule> offRules){
        String offStr = "";

        if(offRules.size() > 0){
            offStr += " --skip-check ";

            for(int i = 0 ; i < offRules.size() ; i++){
                if(offRules.get(i) == null || offRules.get(i).getRuleId() == null)
                    continue;

                offStr += offRules.get(i).getRuleId();

                if(i + 1 < offRules.size())
                    offStr += ",";
            }
        }
        return offStr;
    }

    /**
     * 
     * @param results
     * @return
     */
    private double[] calc(List<ResultResponse> results){
        /* score, high, medium, low, unknown */
        double[] count = new double[5];
        count[0] = 0.0;
        count[1] = 0.0;
        count[2] = 0.0;
        count[3] = 0.0;
        count[4] = 0.0;
        int success = 0;
        int total = 0;
        int severity = 0;
        int totalSeverity = 0;

        for(int i = 0 ; i < results.size(); i++){
            try{
                ResultResponse result = results.get(i);

                total += 1;

                switch(result.getLevel()){
                    case "High":
                        if(result.getStatus() == "passed"){
                            success += 1;
                            severity += 3;
                            count[1] += 1;
                        }
                        totalSeverity += 3;
                    case "Medium":
                        if(result.getStatus() == "passed"){
                            success += 1;
                            severity += 2;
                            count[2] += 1;
                        }
                        totalSeverity += 2;
                    case "Low":
                        if(result.getStatus() == "passed"){
                            success += 1;
                            severity += 1;
                            count[3] += 1;
                        }
                        totalSeverity += 1;
                    default:
                        if(result.getStatus() == "passed"){
                            success += 1;
                            severity += 1;
                            count[4] += 1;
                        }
                        totalSeverity += 1;
                }
            }
            catch(NullPointerException e){
                continue;
            }
        }
        
        double down = (total * totalSeverity);

        if(down == 0.0)
            count[0] = 0.0;
        else
            count[0] = Math.round((double)(total * totalSeverity)/ down) * 10 / 10.0;

        return count;
    }
}