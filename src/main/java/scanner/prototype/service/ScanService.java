package scanner.prototype.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.CheckListDetailDto;
import scanner.prototype.env.Env;
import scanner.prototype.exception.ScanException;
import scanner.prototype.model.CustomRule;
import scanner.prototype.response.CheckResponse;
import scanner.prototype.response.ParseResponse;
import scanner.prototype.response.ResultResponse;
import scanner.prototype.response.ScanResponse;
import scanner.prototype.visualize.ParserRequest;


@Service
@RequiredArgsConstructor
public class ScanService {

    private final ParserRequest parserReq;
    private final CheckListService checkListService;

    /**
     * 
     * @param args
     * @return
     * @throws Exception
     */
    public ScanResponse<?> scanTerraform(String args) 
    throws Exception 
    {
        ScanResponse<?> scanResult;
        Process p;
         
        try {
            List<CustomRule> offRules = checkListService.retrieveOffEntity();
            String offStr = "";

            if(offRules.size() > 0){
                offStr += " --skip-check ";

                for(int i = 0 ; i < offRules.size() ; i++){
                    offStr += offRules.get(i).getRuleId();
                    offStr += ",";
                }
            }

            File file = new File(Env.FILE_UPLOAD_PATH.getValue() + File.separator + args);
            String[] cmd = {"bash", "-l", "-c", Env.SHELL_COMMAND_RAW.getValue() + File.separator + args + offStr + Env.EXTERNAL_CHECK.getValue()};

            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));

            scanResult = resultToJson(br, args);
            FileUtils.deleteDirectory(file);
            p.waitFor();
            p.destroy();
            
            return scanResult;
        } catch (Exception e) {
            throw new ScanException("Scan Error.");
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
     */
    public ScanResponse<?> resultToJson(BufferedReader br, String path) 
    throws IOException
    {
        String rawResult;
        StringBuilder sb = new StringBuilder();
        CheckResponse check = new CheckResponse();
        ResultResponse result = new ResultResponse();
        List<ResultResponse> resultLists = new ArrayList<>();
        ParseResponse parse = new ParseResponse(parserReq.getTerraformParsingData(path)); 
        
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
}